package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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
import com.example.administrator.newsdf.pzgc.utils.LogUtil;

public class CheckRectificationWebActivity extends AppCompatActivity {
    boolean lean = true;
    private TextView text;
    private WebView mWebView;
    private Context mContext;
    private TextView reloadTv;
    private RelativeLayout linProbar, nonet;
    private String url = "http://192.168.20.33:8080/#/";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_web);
        setContentView(R.layout.activity_check_task_web);
        mContext = this;
        linProbar = (RelativeLayout) findViewById(R.id.lin_probar);
        nonet = (RelativeLayout) findViewById(R.id.nonet);
        mWebView = (WebView) findViewById(R.id.check);
        text = (TextView) findViewById(R.id.text);
        reloadTv = (TextView) findViewById(R.id.reload_tv);
        textclick();

        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setUseWideViewPort(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        //AndroidtoJS类对象映射到js的view对象
        mWebView.addJavascriptInterface(new AndroidtoJs(mContext, "str"), "view");
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
        //如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //Android使用WebView加载https地址打不开的问题  小米
                handler.proceed();
                LogUtil.i("sss", "onReceivedSslError");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
                if (lean) {
                    mWebView.setVisibility(View.VISIBLE);
                    nonet.setVisibility(View.GONE);
                    linProbar.setVisibility(View.GONE);
                } else {
                    mWebView.setVisibility(View.GONE);
                    nonet.setVisibility(View.VISIBLE);
                    linProbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtil.i("sss", "加载开始");
                //加载开始
                linProbar.setVisibility(View.VISIBLE);
                nonet.setVisibility(View.GONE);
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                LogUtil.i("sss", "处理网页加载失败时");
                lean = false;
                //6.0以上执行
                linProbar.setVisibility(View.GONE);
                nonet.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
            }
        });
    }

    private void textclick() {
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append("数据获取失败,请检查网络后再\n重试..");
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mWebView.loadUrl(url);
                lean = true;
            }
        };
        style.setSpan(clickableSpan, 15, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        reloadTv.setText(style);
        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 15, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        reloadTv.setMovementMethod(LinkMovementMethod.getInstance());
        reloadTv.setText(style);
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
      mWebView.clearCache(true);
//        destroyWebView();
    }

}
