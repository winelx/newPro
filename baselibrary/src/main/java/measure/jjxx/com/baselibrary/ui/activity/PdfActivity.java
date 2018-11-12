package measure.jjxx.com.baselibrary.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import measure.jjxx.com.baselibrary.R;
import measure.jjxx.com.baselibrary.utils.BaseDialog;
import measure.jjxx.com.baselibrary.utils.PermissionUtils;

public class PdfActivity extends AppCompatActivity {
    private PDFView pdfView;
    private String paths;
    private Context mContext;
    private String pathname;
    private String url = "";
    private File f;
    private OutputStream os;
    private InputStream is;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final String path = "file:///android_asset/pdfjs/web/viewer.html?file=";
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        mContext = this;
        permissionUtils = new PermissionUtils();
        paths = getExternalCacheDir().getPath();
        paths = paths.replace("cache", "PDF/");
        Intent intent = getIntent();
        url = intent.getStringExtra("http");
        pathname = url.substring(url.lastIndexOf("/") + 1, url.length());
        pdfView = (PDFView) findViewById(R.id.pdfView);
        findViewById(R.id.base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PermissionUtils permissionUtils = new PermissionUtils();
        boolean isAllGranted = permissionUtils.checkPermissionAllGranted(mContext, PERMISSIONS_STORAGE);
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            getdata();
        } else {
            // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void getdata() {
        //判断路径下下你文件是否存在
        boolean status = fileIsExists(paths + pathname);
        if (status) {
            //存在
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                pdfView.fromFile(f)
                        .defaultPage(1)
                        .enableSwipe(true)
                        .load();
            }
        } else {
            //不存在
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //判断当前系统是否高于或等于6.0
                    final String download = download(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                boolean status = fileIsExists(paths + pathname);
                                pdfView.fromFile(f)
                                        .defaultPage(1)
                                        .enableSwipe(true)
                                        .load();

                            }
                        }
                    });
                }
            }).start();

        }
    }


    private String download(String path) {
        try {
            URL url = new URL(path);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            is = conn.getInputStream();
            File file = new File(paths);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            final String fileName = paths + pathname;
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            os = new FileOutputStream(fileName);
            //写数据
            int progress = 0;
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    /**
     * 判断路径下指定文件是否存在
     */
    public boolean fileIsExists(String path) {
        try {
            f = new File(path);
            if (!f.exists()) {
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


    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                getdata();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                BaseDialog.openAppDetails(mContext);
            }
        }
    }

}
