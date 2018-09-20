package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

public class CheckTaskWebActivity extends AppCompatActivity {
    private TextView text;
    private RelativeLayout linProbar;
    private WebView mWebView;
    private Context mContext;
    private String url = "http://192.168.20.33:8080/m/TaskList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task_web);
        linProbar = (RelativeLayout) findViewById(R.id.lin_probar);
        mWebView = (WebView) findViewById(R.id.check);
        text = (TextView) findViewById(R.id.text);
        mContext = this;
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setUseWideViewPort(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        //AndroidtoJS类对象映射到js的view对象
        mWebView.addJavascriptInterface(new AndroidtoJs(mContext, "task"), "view");
        //如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //Android使用WebView加载https地址打不开的问题  小米
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
//                Toast.makeText(WebActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //加载开始
//                Toast.makeText(WebActivity.this, "加载开始", Toast.LENGTH_SHORT).show();
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行
                linProbar.setVisibility(View.GONE);
            }


        });
        //加载url
        mWebView.loadUrl(url);
        //加载进度
        mWebView.setWebChromeClient(new WebChromeClient() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                text.setText(newProgress + "%");
                if (newProgress == 100) {
                    linProbar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void finsh() {
        this.finish();
    }

    //退出界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (url.equals(mWebView.getUrl())) {
                this.finish();
            } else {
                //后退
                mWebView.goBack();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mWebView.clearCache(true);
//        destroyWebView();
    }

    private void destroyWebView() {
        mWebView.removeAllViews();
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            // clearView()应该改为loadUrl(“about:blank”)，因为clearView()现在已经被废弃了
            mWebView.loadUrl("about:blank");
            mWebView.freeMemory();
            mWebView.pauseTimers();
            // mWebView.destroy()和mWebView = null做同样的事情
            mWebView = null;
        }
    }

}
