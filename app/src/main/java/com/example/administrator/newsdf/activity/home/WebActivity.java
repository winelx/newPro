package com.example.administrator.newsdf.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.administrator.newsdf.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * description:  使用webview展示pdf、文档和表格
 *
 * @author lx
 *         date: 2018/3/12 0012 上午 9:15
 *         update: 2018/3/12 0012
 *         version:
 */
public class WebActivity extends AppCompatActivity {
    WebView pdfViewerWeb;
    private ProgressBar pg1;
    String Url;
    private IconTextView com_back;
    private String downloadUrl = "http://117.187.27.78:8081/baseframe/upload/2018/01/23/bbceda7901ba453d912b340a5badc081.pdf";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbe);
        com_back = (IconTextView) findViewById(R.id.com_back);
        pdfViewerWeb = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        Url = intent.getStringExtra("http");
        WebSettings settings = pdfViewerWeb.getSettings();
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        //AndroidtoJS类对象映射到js的test对象
        pdfViewerWeb.addJavascriptInterface(new AndroidtoJs(), "android");
        pdfViewerWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }

        });
        pdfViewerWeb.setWebChromeClient(new WebChromeClient());

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String download = download();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//api >= 19
                            pdfViewerWeb.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + download);

                        }
                    }
                });
            }
        }).start();
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void back() {
            WebActivity.this.finish();
        }
    }

    //下载具体操作
    private String download() {
        try {
            URL url = new URL(Url);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            //获得长度
            int contentLength = conn.getContentLength();
            //创建文件夹 MyDownLoad，在存储卡下
            String dirName = Environment.getExternalStorageDirectory() + "/MyDownLoad/";
            File file = new File(dirName);

            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            final String fileName = dirName + "invoice.pdf";
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
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
