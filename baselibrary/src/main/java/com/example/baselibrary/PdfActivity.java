package com.example.baselibrary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.baselibrary.view.BaseActivity;
import com.example.baselibrary.view.PermissionListener;
import com.github.barteksc.pdfviewer.PDFView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PdfActivity extends BaseActivity {
    private PDFView pdfView;
    private String paths;
    private Context mContext;
    private String pathname;
    private String url = "";
    private OutputStream os;
    private InputStream is;
    private File files;
    private boolean lean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        mContext = this;
        Intent intent = getIntent();
        url = intent.getStringExtra("http");
        pathname = url.substring(url.lastIndexOf("/") + 1, url.length());
        paths = getExternalCacheDir().getPath().replace("cache", "PDF/");
        pdfView = (PDFView) findViewById(R.id.pdfView);
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
                }else {
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
                pdfView.fromFile(files)
                        .defaultPage(1)
                        .enableSwipe(true)
                        .load();

            }
        } else {

            OkGo.get(url)
                    .execute(new FileCallback(paths, pathname) {
                        @Override
                        public void onSuccess(File file, Call call, Response response) {

                            pdfView.fromFile(file)
                                    .defaultPage(1)
                                    .enableSwipe(true)
                                    .load();
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
}

