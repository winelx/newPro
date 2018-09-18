package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.lang.reflect.Method;

public class CheckStatisticalActivity extends BaseActivity {
    private WebView webView;
    private TextView titleView;
    private LinearLayout linearData;
    private TextView lonearLogading;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_check_statistical);
        titleView = (TextView) findViewById(R.id.titleView);
        linearData = (LinearLayout) findViewById(R.id.linear_data);
        lonearLogading = (TextView) findViewById(R.id.lonear_logading);
        webView = (WebView) findViewById(R.id.check_webview);
        titleView.setText("统计报表");
        initView();
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strurl = webView.getUrl();
                String str = "https://www.jianshu.com/u/fa272f63280a";
                if (strurl.equals(str)) {
                    webView.destroy();
                    finish();
                } else {
                    // 返回前一个页面
                    webView.goBack();
                }
            }
        });
    }

    private void initView() {
        disableAccessibility(mContext);
        webView.addJavascriptInterface(new NativeInterface(this), "AndroidNative");
        //垂直不显示
        webView.setVerticalScrollBarEnabled(false);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(false);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                }

                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
                if (newProgress == 100) {
                    linearData.setVisibility(View.GONE);
                } else {
                    lonearLogading.setText("加载完成:" + newProgress + "%");
                }
            }
        });
        webView.loadUrl("https://www.jianshu.com/u/fa272f63280a");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        String strurl = webView.getUrl();
        String str = "https://www.jianshu.com/u/fa272f63280a";
        if (strurl.equals(str)) {
            webView.destroy();
            finish();
            return true;
        } else {
            // 返回前一个页面
            webView.goBack();
            return false;
        }
    }

    public static void disableAccessibility(Context context) {
        if (Build.VERSION.SDK_INT == 17/*4.2 (Build.VERSION_CODES.JELLY_BEAN_MR1)*/) {
            if (context != null) {
                try {
                    AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
                    if (!am.isEnabled()) {
                        //Not need to disable accessibility
                        return;
                    }

                    Method setState = am.getClass().getDeclaredMethod("setState", int.class);
                    setState.setAccessible(true);
                    setState.invoke(am, 0);/**{@link AccessibilityManager#STATE_FLAG_ACCESSIBILITY_ENABLED}*/
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

    // 创建要注入的 Java 类
    public class NativeInterface {

        private Context mContext;

        public NativeInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void hello() {
            Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void hello(String params) {
            Toast.makeText(mContext, params, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public String getAndroid() {
            Toast.makeText(mContext, "getAndroid", Toast.LENGTH_SHORT).show();
            return "Android data";
        }

    }
}
