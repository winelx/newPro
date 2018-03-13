package com.example.administrator.newsdf.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.administrator.newsdf.R;
import com.joanzapata.pdfview.PDFView;

/**
 * description:  使用webview展示pdf、文档和表格
 *
 * @author lx
 *         date: 2018/3/12 0012 上午 9:15
 *         update: 2018/3/12 0012
 *         version:
 */
public class WebActivity extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar pg1;
    private PDFView pdfview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbe);
        Intent intent = getIntent();
        String Url = intent.getStringExtra("http");

       init();
        mWebView.loadUrl(Url);

        //方式2：加载apk包中的html页面
        //  mWebView.loadUrl("file:///android_asset/test.html");
        //方式3：加载手机本地的html页面
//        mWebView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
        // 方式4： 加载 HTML 页面的一小段内容
        //  WebView.loadData(String data, String mimeType, String encoding);

    }

    private void init() {
        // TODO 自动生成的方法存根
        mWebView = (WebView) findViewById(R.id.web);
        pg1 = (ProgressBar) findViewById(R.id.progressBar1);

        mWebView.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting = mWebView.getSettings();
        //设置webview支持javascript脚本
        seting.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    //加载完网页进度条消失
                    pg1.setVisibility(View.GONE);
                } else {
                    //开始加载网页时显示进度条
                    pg1.setVisibility(View.VISIBLE);
                    //设置进度值
                    pg1.setProgress(newProgress);
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//恢复pauseTimers状态
        mWebView.resumeTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        mWebView.pauseTimers();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 重写返回键
     * 设置返回键动作（防止按返回键直接退出程序)
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //当webview不是处于第一页面时，返回上一个页面
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
                //当webview处于第一页面时,直接退出程序
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
