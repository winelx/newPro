package com.example.baselibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.baselibrary.view.BaseActivity;
import com.example.baselibrary.view.PermissionListener;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoView;

public class PdfActivity extends BaseActivity {

    private String paths;
    private Context mContext;
    private String pathname;
    private String url = "";
    private OutputStream os;
    private InputStream is;
    private File files;
    private boolean lean = false;


    private ViewPager vpPdf;
    private LayoutInflater mInflater;
    private ParcelFileDescriptor mDescriptor;
    private PdfRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        mContext = this;
        mInflater = LayoutInflater.from(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("http");
        pathname = url.substring(url.lastIndexOf("/") + 1, url.length());
        paths = getExternalCacheDir().getPath().replace("cache", "PDF/");
        vpPdf = findViewById(R.id.vp_pdf);
        files = new File(paths + pathname);
        permisssion();
        findViewById(R.id.base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lean) {
                    permisssion();
                    Toast.makeText(mContext, "重新请求中..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "pdf已经下载完毕..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getdata() {
        //判断路径下下你文件是否存在
        boolean status = fileIsExists(files);
        if (status) {
            //存在
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    openRender(files);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            /*下载psf*/
            OkGo.get(url)
                    .execute(new FileCallback(paths, pathname) {
                        @Override
                        public void onSuccess(File file, Call call, Response response) {
                            try {
                                openRender(files);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            lean = false;
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            lean = true;
                            Toast.makeText(mContext, "pdf下载失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                            Log.i("down", currentSize + "---" + totalSize + "---" + progress + "---" + networkSpeed);
                        }
                    });

        }
    }

    /*获取权限*/
    @SuppressLint("InlinedApi")
    public void permisssion() {
        requestRunPermisssion(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                getdata();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(mContext, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 判断路径下指定文件是否存在
     */
    public static boolean fileIsExists(File file) {
        try {
            // 总文件大小
            if (!file.exists()) {
                //不存在
                return false;
            }
        } catch (Exception e) {
            //
            return false;
        }
        //存在
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void openRender(File file) throws IOException {
        //初始化PdfRender
        mDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        if (mDescriptor != null) {
            mRenderer = new PdfRenderer(mDescriptor);
        }
        //初始化ViewPager的适配器并绑定
        MyAdapter adapter = new MyAdapter();
        vpPdf.setAdapter(adapter);
    }

    /**
     * 适配器
     */
    class MyAdapter extends PagerAdapter {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public int getCount() {
            return mRenderer.getPageCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.item_pdf, null);
            PhotoView pvPdf = view.findViewById(R.id.iv_pdf);
            PdfRenderer.Page currentPage = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                currentPage = mRenderer.openPage(position);
            }
            Bitmap bitmap = Bitmap.createBitmap(1080, 1760, Bitmap.Config
                    .ARGB_8888);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            }
            pvPdf.setImageBitmap(bitmap);
            //关闭当前Page对象
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                currentPage.close();
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁需要销毁的视图
            container.removeView((View) object);
        }
    }
}

