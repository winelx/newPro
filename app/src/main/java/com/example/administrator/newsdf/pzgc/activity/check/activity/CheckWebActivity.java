package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;

public class CheckWebActivity extends AppCompatActivity {
    private TextView text;
    private LinearLayout linProbar;
    private WebView mWebView;
    private static CheckWebActivity mContext;
    //    private String url = "http://192.168.20.24:8080/m/#/";
    private String url = "http://192.168.20.33:8080/#/";

    public static CheckWebActivity getInstance() {
        return mContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_web);
        mWebView = (WebView) findViewById(R.id.check);
        mContext = this;
        linProbar = (LinearLayout) findViewById(R.id.lin_probar);
        text = (TextView) findViewById(R.id.text);

        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setUseWideViewPort(true);

        mWebView.getSettings().setDomStorageEnabled(true);
        //AndroidtoJS类对象映射到js的view对象
        mWebView.addJavascriptInterface(new AndroidtoJs(mContext), "view");
        //如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                // TODO Auto-generated method stub
                //允许https://的访问
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

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
                //加载失败
//          Toast.makeText(WebActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        //加载url
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                text.setText(newProgress + "");
                if (newProgress == 100) {
                    linProbar.setVisibility(View.GONE);
                }
            }
        });
        LogUtil.i("ss", mWebView.getUrl());
    }

    public void finsh() {
        mContext.finish();
    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (url.equals(mWebView.getUrl())) {
                finish();
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
        destroyWebView();
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
