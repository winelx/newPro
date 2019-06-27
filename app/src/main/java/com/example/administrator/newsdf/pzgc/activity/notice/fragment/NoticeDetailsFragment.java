package com.example.administrator.newsdf.pzgc.activity.notice.fragment;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.util.List;

import androidx.navigation.Navigation;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author： lx
 * @创建时间： 2019/5/29 0029 10:24
 * @说明： 通知公告详情
 **/
public class NoticeDetailsFragment extends LazyloadFragment implements View.OnClickListener {
    private LinearLayout titlel;
    private TextView com_title;
    private TextView text;
    private WebView mWebView;
    private RelativeLayout linProbar, nonet;
    private List<Cookie> cookies;
    boolean lean = true;
    private String ids, url;

    @Override
    protected int setContentView() {
        return R.layout.activity_check_task_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {
        ids = getArguments().getString("ids") + "";
 url = Requests.networks + "admin/sys/sysproclamation/publicdelByApp?id=" + ids;
 //url =  "http://192.168.20.35:8088/generic/web/viewer.html";
        //获取cookie
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        cookies = cookieStore.getCookie(httpUrl);
        //设置标题
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("公告详情");
        text = (TextView) findViewById(R.id.text);
        titlel = (LinearLayout) findViewById(R.id.toolbar_title);
        titlel.setVisibility(View.VISIBLE);
        nonet = (RelativeLayout) findViewById(R.id.nonet);
        nonet.setOnClickListener(this);
        linProbar = (RelativeLayout) findViewById(R.id.lin_probar);
        rootView.findViewById(R.id.com_back).setOnClickListener(this);
        mWebView = rootView.findViewById(R.id.check);
        sycCook();
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        //允许js
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(false);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        mWebView.clearCache(true);
        //不使用缓存，只从网络获取数据.
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
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
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //    加载完成
                linProbar.setVisibility(View.GONE);
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

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                //返回
                Navigation.findNavController(v).navigateUp();
                break;
            case R.id.nonet:
                linProbar.setVisibility(View.VISIBLE);
                nonet.setVisibility(View.GONE);
                mWebView.loadUrl(url);
                break;
            default:
                break;
        }
    }

    /**
     * 同步cook
     */
    public void sycCook() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(Requests.networks, cookies.toString());
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }
}
