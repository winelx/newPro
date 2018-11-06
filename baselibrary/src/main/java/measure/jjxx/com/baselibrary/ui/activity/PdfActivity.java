package measure.jjxx.com.baselibrary.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import measure.jjxx.com.baselibrary.R;

public class PdfActivity extends AppCompatActivity {
    private PDFView pdfView;
    String paths;
    String pathname;
    String url = "";
    private File f;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final String path = "file:///android_asset/pdfjs/web/viewer.html?file=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        paths = getExternalCacheDir().getPath();
        paths = paths.replace("cache", "PDF/");
        Intent intent = getIntent();
        url = intent.getStringExtra("http");
        pathname = url.substring(url.lastIndexOf("/")+1, url.length());
        pdfView = (PDFView) findViewById(R.id.pdfView);
        /**
         *判断路径下下你文件是否存在
         */
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
        findViewById(R.id.base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private String download(String path) {
        try {
            URL url = new URL(path);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
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
            OutputStream os = new FileOutputStream(fileName);
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            os.close();
            is.close();
            return  fileName;
        } catch (Exception e) {
            e.printStackTrace();
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


}
