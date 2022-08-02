package com.example.administrator.yanghu.pzgc.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.utils.AndroidtoJs;
import com.example.baselibrary.base.LazyloadFragment;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import static android.app.Activity.RESULT_OK;

public class HomesFragment extends LazyloadFragment {
    boolean lean = true;
    private FrameLayout mFrameLayout;
    private WebView mWebView;
    private LinearLayout ivError;
    private ProgressBar linProbar;
    private Uri[] results;
    private List<Cookie> cookies;
    //相机回调结果
    private final static int PHOTO_REQUEST = 101;
    // 表单的数据信息
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    // 表单的结果回调
    private final static int FILECHOOSER_RESULTCODE = 1;
    boolean status = true;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_new_staff;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void init() {
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        cookies = cookieStore.getCookie(httpUrl);
        findViewById(R.id.back).setOnClickListener(v -> mWebView.reload());
        //错误提示
        ivError = (LinearLayout) findViewById(R.id.ivError);
        linProbar = (ProgressBar) findViewById(R.id.lin_probar);
        mFrameLayout = (FrameLayout) findViewById(R.id.mFrameLayout);
        mWebView = (WebView) findViewById(R.id.mWebView);
        //设置webiew
        initWebView();
        sycCook();
        mWebView.loadUrl(Requests.networks + "/h5/taskcheck/index.html#/empty");
    }

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


    @SuppressLint("JavascriptInterface")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        //是否允许js脚本
        settings.setJavaScriptEnabled(true);
        //开启本地DOM存储
        settings.setDomStorageEnabled(true);
        //是否显示缩放按钮，默认false
        settings.setBuiltInZoomControls(false);
        ////设置js可以直接打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //播放音频，多媒体需要用户手动？设置为false为可自动播放
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        //是否使用缓存
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true);
        //下面两个解决网页自适应问题
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        /* 提高网页渲染的优先级 */
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setTextZoom(100);
        //设置编码
        settings.setDefaultTextEncodingName("utf-8");
        /* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(true);
        //AndroidtoJS类对象映射到js的view对象
        mWebView.addJavascriptInterface(new AndroidtoJs((Activity) mContext), "phone");
        //加载进度
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                return true;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

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
                if (error.getPrimaryError() == 404) {
                    lean = false;
                    //6.0以上执行
                    linProbar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
                if (lean) {
                    mWebView.setVisibility(View.VISIBLE);
                    linProbar.setVisibility(View.GONE);
                } else {
                    mWebView.setVisibility(View.GONE);
                    linProbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //加载开始
                linProbar.setVisibility(View.VISIBLE);
            }

            //处理网页加载失败时
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (!error.getDescription().toString().contains("ERR_FAILED")) {
                    lean = false;
                    //6.0以上执行
                    linProbar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * @Author lx
     * @创建时间 2019/7/5 0005 11:14
     * @说明 加载监听
     **/
    public void loading() {
        /* ,重写WebViewClient可以监听网页的跳转和资源加载等等... */
        mWebView.setWebViewClient(new WebViewClient() {
            //在开始加载网页时会回调
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                ivError.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // 显示错误界面
                    ivError.setVisibility(View.VISIBLE);
                    linProbar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.GONE);
                    status = false;
                } else {
                    status = true;
                }
            }


            // 新版本，只会在Android6及以上调用
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {
                    // 在这里显示自定义错误页
                    ivError.setVisibility(View.VISIBLE);
                    linProbar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.GONE);
                    status = false;
                } else {
                    status = true;
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("https://") || url.contains("http://")) {
                    view.loadUrl(url);
                } else {
                    try {
                        // 以下固定写法,表示跳转到第三方应用
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } catch (Exception e) {
                        // 防止没有安装的情况
                        e.printStackTrace();
                    }

                }
                return true;
            }

            //加载完成的时候会回调
            @Override
            public void onPageFinished(WebView webView, String s) {
                if (status) {
                    linProbar.setVisibility(View.GONE);
                    ivError.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                } else {
                    linProbar.setVisibility(View.GONE);
                    ivError.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            //接受相册返回数据
            if (null == mUploadMessage && null == mUploadCallbackAboveL) {
                return;
            }
            if (data != null) {
                if (mUploadCallbackAboveL != null) {
                    //return the compressed file path
                    onActivityResultAboveL(requestCode, resultCode, data);
                }
            } else {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }

        } else if (requestCode == PHOTO_REQUEST) {
            //接收相机返回数据
            if (null == mUploadMessage && null == mUploadCallbackAboveL) {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }

    }

    /**
     * 回调图片上传给webview
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @SuppressLint("HandlerLeak")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (mUploadCallbackAboveL != null) {
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        results = null;
                    } else {
                        String dataString = data.getDataString();
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            results = new Uri[clipData.getItemCount()];
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                results[i] = item.getUri();
                            }
                        }
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
        } else if (requestCode == PHOTO_REQUEST) {
            results = null;
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }

    }
}
