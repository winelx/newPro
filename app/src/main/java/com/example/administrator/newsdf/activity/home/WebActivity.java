package com.example.administrator.newsdf.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    List<String> list = new ArrayList<>();
    String pathname;
    private IconTextView com_back;
    String paths = "/storage/emulated/0/Android/data/com.example.administrator.newsdf/MyDownLoad/";

    private boolean status = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbe);
        com_back = (IconTextView) findViewById(R.id.com_back);
        pdfViewerWeb = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        //拿到pdf路径
        Url = intent.getStringExtra("http");
        //将字符串转成集合，
        list = stringToList(Url);
        //通集合拿到pdf的名称
        pathname = list.get(list.size() - 1);
        //webview的配置文件
        WebSettings settings = pdfViewerWeb.getSettings();
        //设置WebView是否应该保存密码。默认是正确的。
        settings.setSavePassword(false);
        //是否支持js  Tells the WebView to enable JavaScript execution.
        settings.setJavaScriptEnabled(true);
        //  是否在文件模式下运行JavaScript 是否允许URL访问其他文件的内容计划的url
        settings.setAllowFileAccessFromFileURLs(true);
        //是否在文件模式下运行JavaScript 是否允许URL访问来自任何来源的内容。
        settings.setAllowUniversalAccessFromFileURLs(true);
        //是否启用对viewport元标签的支持。
        settings.setUseWideViewPort(true);
        //是否允许放大界面
        settings.setLoadWithOverviewMode(true);
        //WebView是否应该使用内置的缩放机制。
        settings.setBuiltInZoomControls(true);
        //WebView是否应该显示屏幕缩放控件。
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
        /**
         *判断路径下下你文件是否存在
         */
        status = fileIsExists(paths + pathname);
        if (status) {
            //存在
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                pdfViewerWeb.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + paths + pathname);
            }
        } else {
            //不存在
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String download = download(Url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                pdfViewerWeb.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + download);
                            }
                        }
                    });
                }
            }).start();
        }
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

    /**
     * 下载文件的具体操作
     */

    private String download(String path) {
        String strpath = "/storage/emulated/0/Android/data/com.example.administrator.newsdf";
        try {
            URL url = new URL(path);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            //获得长度
            int contentLength = conn.getContentLength();
            //创建文件夹 MyDownLoad，在存储卡下
            String dirName = strpath+ "/MyDownLoad/";
            File file = new File(dirName);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            final String fileName = dirName + pathname;
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

    //截取路径转成集合
    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {
            //字符串为空
        } else {
            //字符串不为空
            String str[] = strs.split("/");
            return Arrays.asList(str);
        }
        return null;
    }

    /**
     * 判断路径下指定文件是否存在
     */
    public boolean fileIsExists(String path) {
        try {
            File f = new File(path);
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
