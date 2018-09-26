package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
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
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


public class CheckTaskWebActivity extends AppCompatActivity {
    boolean lean = true;
    private TextView text;
    private WebView mWebView;
    private Context mContext;
    private TextView reloadTv;
    private List<Cookie> cookiesList;
    private RelativeLayout linProbar, nonet;
    private String url = "http://192.168.20.33:8080/m/TaskList";
    CookieStore cookieStore;
    HttpUrl httpUrl;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task_web);
        mContext = this;
        cookiesList = new ArrayList<>();
        linProbar = (RelativeLayout) findViewById(R.id.lin_probar);
        nonet = (RelativeLayout) findViewById(R.id.nonet);
        mWebView = (WebView) findViewById(R.id.check);
        text = (TextView) findViewById(R.id.text);
        reloadTv = (TextView) findViewById(R.id.reload_tv);
        textclick();
        cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        httpUrl = HttpUrl.parse(Requests.networks);
        cookiesList = cookieStore.getCookie(httpUrl);
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setUseWideViewPort(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        //AndroidtoJS类对象映射到js的view对象
        mWebView.addJavascriptInterface(new AndroidtoJs(mContext, "task"), "view");

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

                //加载开始
                linProbar.setVisibility(View.VISIBLE);
                nonet.setVisibility(View.GONE);
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                lean = false;
                //6.0以上执行
                linProbar.setVisibility(View.GONE);
                nonet.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
            }
        });
//        syncCookieToWebView(Requests.networks, cookiesList.toString());
        //同步cooking
//        Boolean status = syncCookie(mContext, Requests.networks, cookiesList.toString());
//        if (status) {
//            ToastUtils.showLongToast("同步成功");
//        } else {
//            ToastUtils.showLongToast("同步失败");
//        }
        setCookie(mContext, cookiesList.get(0)+"");
        //加载url
        mWebView.loadUrl(url);
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

    /**
     * 同步一下cookie
     */
    public static boolean synCookies(Context context, String url, String cooking) {
        /**
         * 将cookie同步到WebView
         * @param url WebView要加载的url
         * @param cookie 要同步的cookie
         * @return true 同步cookie成功，false同步cookie失败
         * @Author JPH
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }

        CookieManager cookieManager = CookieManager.getInstance();
        //如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        cookieManager.setCookie(Requests.networks, cooking);
        String newCookie = cookieManager.getCookie(Requests.networks);
        LogUtil.d("cook", newCookie);
        return TextUtils.isEmpty(newCookie) ? false : true;

    }

    public static boolean syncCookie(Context mContext, String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(mContext);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }

    public void synCookies(Context context, String url) {

//        CookieSyncManager.createInstance(context);
//        CookieManager cookieManager = CookieManager.getInstance();
//        CookieManager.getInstance().setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
//        }
//
////      cookieManager.removeSessionCookie();//移除
//        cookieManager.setCookie(url, cookiesList.toString());
//        String newCookie = cookieManager.getCookie(url);
//        LogUtil.d("cook", newCookie);
//        CookieSyncManager.getInstance().sync();
//

        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, "JSESSIONID=" + cookieManager.getCookie("JSESSIONID") +
                ";token=" + cookieManager.getCookie("token") + ";deliveryManName=" + cookieManager.getCookie("deliveryManName")
        );
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除  
        //cookies是在HttpClient中获得的cookie 
        cookieManager.setCookie(url, cookiesList.toString());
        CookieSyncManager.getInstance().sync();


    }

    @SuppressWarnings("deprecation")
    public void Cookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }
        cookieManager.removeAllCookie();
        List<Cookie> cookies = cookieStore.getCookie(httpUrl);
        StringBuffer sb = new StringBuffer();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.name();
            String cookieValue = cookie.value();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        String[] cookie = sb.toString().split(";");
        for (int i = 0; i < cookie.length; i++) {
            Log.d("cookie", cookie[i]);
            // cookies是在HttpClient中获得的cookie
            cookieManager.setCookie(url, cookie[i]);
        }
        CookieSyncManager.getInstance().sync();
    }

    public static void setCookie(Context context, String cookie) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(Requests.networks, cookie);
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
