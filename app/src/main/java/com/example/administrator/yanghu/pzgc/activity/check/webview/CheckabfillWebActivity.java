package com.example.administrator.yanghu.pzgc.activity.check.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.LoginActivity;
import com.example.administrator.yanghu.pzgc.activity.check.activity.AndroidtoJs;
import com.example.administrator.yanghu.pzgc.activity.check.activity.CheckRectificationWebActivity;
import com.example.administrator.yanghu.pzgc.activity.check.activity.CheckTaskWebActivity;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.yanghu.pzgc.utils.PopCameraFragment;
import com.example.administrator.yanghu.pzgc.utils.TakePictureManager;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.QrConfig;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.PermissionListener;
import com.example.baselibrary.zxing.android.CaptureActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * 说明：AB类风险管控
 * 创建时间： 2022/3/3 0003 9:39
 *
 * @author winelx
 */
public class CheckabfillWebActivity extends BaseActivity {
    boolean lean = true;
    private TextView text;
    private WebView mWebView;
    private Context mContext;
    private TextView reloadTv;
    private RelativeLayout linProbar, nonet;
    private String url;
    private List<Cookie> cookies;
    private TakePictureManager takePictureManager;
    private Uri[] results;
    //相机回调结果
    private final static int PHOTO_REQUEST = 101;
    // 表单的数据信息
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    // 表单的结果回调
    private final static int FILECHOOSER_RESULTCODE = 1;
    private static final int REQUEST_CODE = 0x0001;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("color") != null) {
            ImmersionBar.with(this).statusBarColor(getIntent().getStringExtra("color")).fitsSystemWindows(false).init();
        } else {
            ImmersionBar.with(this).statusBarColor("#5096F8").statusBarDarkFont(true).fitsSystemWindows(false).init();
        }
        setContentView(R.layout.activity_check_task_web);
        mContext = this;
        hideBottomUIMenu();
        takePictureManager = new TakePictureManager(this);
        url = getIntent().getStringExtra("url");
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        cookies = cookieStore.getCookie(httpUrl);
        linProbar = findViewById(R.id.lin_probar);
        nonet = findViewById(R.id.nonet);
        mWebView = findViewById(R.id.check);
        text = findViewById(R.id.text);
        reloadTv = (TextView) findViewById(R.id.reload_tv);
        textclick();
        initWebView();
        sycCook();
        //加载url
        mWebView.loadUrl(url);

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
        mWebView.addJavascriptInterface(new AndroidtoJss((Activity) mContext, "str"), "phone");
        //加载进度
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }

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
                if (error.getPrimaryError() == 404) {
                    lean = false;
                    //6.0以上执行
                    linProbar.setVisibility(View.GONE);
                    nonet.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成
                mWebView.setVisibility(View.VISIBLE);
                nonet.setVisibility(View.GONE);
                linProbar.setVisibility(View.GONE);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //加载开始
                linProbar.setVisibility(View.VISIBLE);
                nonet.setVisibility(View.GONE);
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
                    nonet.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                }
            }
        });
    }

    @SuppressLint({"CheckResult", "HandlerLeak"})
    private void take() {
        requestRunPermisssion(new String[]{Enum.CAMERA, Enum.FILEWRITE, Enum.FILEREAD}, new PermissionListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onGranted() {
                new PopCameraFragment(new NetworkAdapter() {
                    @Override
                    public void onsuccess(String string) {
                        super.onsuccess(string);
                        if ("相机".equals(string)) {
                            takePictureManager.startTakeWayByCarema();
                        } else {
                            //相册多选
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(intent, FILECHOOSER_RESULTCODE);
                        }
                    }

                    @Override
                    public void onerror() {
                        super.onerror();
                        mUploadCallbackAboveL.onReceiveValue(null);
                        mUploadCallbackAboveL = null;
                    }
                }).show(getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    if (permission.equals(Enum.CAMERA)) {
                        BaseDialogUtils.openAppDetails(mContext, "上传图片需要相机,请到权限管理中心打开");
                    } else {
                        BaseDialogUtils.openAppDetails(mContext, "APP需要手机存储权限,请到权限管理中心打开");
                    }
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == QrConfig.REQUEST_WEB) {
            startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                    .putExtra("url", data.getStringExtra("url")));
        } else if (resultCode == QrConfig.REQUEST_H5) {
            getBaseUrl(data.getStringExtra("appid"), new NetworkAdapter() {
                @Override
                public void onsuccess(String base) {
                    super.onsuccess(base);
                    startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                            .putExtra("url", base + data.getStringExtra("url"))
                            .putExtra("color", "#5096F8"));
                }
            });
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
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
        } else if (requestCode == REQUEST_CODE) {
            /*二维码扫码*/
            startActivityForResult(new Intent(mContext, CaptureActivity.class), REQUEST_CODE);
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
    @SuppressWarnings("null")
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (mUploadCallbackAboveL != null) {
                if (resultCode == Activity.RESULT_OK) {
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
            Uri imageContentUri = TakePictureManager.getImageContentUri(mContext, new File(takePictureManager.getImgPath()));
            if (imageContentUri != null) {
                results = new Uri[1];
                results[0] = imageContentUri;
            } else {
                results = null;
            }
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
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return false;
        }
        if (mWebView.canGoBack()) {
            //获取webView的浏览记录
            WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
            //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                mWebView.goBack();
                return true;

            }
        } else {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //缓存
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
//        destroyWebView();
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

    protected void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = ((Activity) mContext).getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = ((Activity) mContext).getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public class AndroidtoJss {
        private Activity activity;
        private String str;

        public AndroidtoJss(Activity activity, String str) {
            this.activity = activity;
            this.str = str;
        }

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void back() {
            activity.finish();
            try {
                LiveDataBus.get().with("mynotast").postValue("刷新待办列表");
            } catch (Exception e) {

            }
        }

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void finsh() {
            activity.finish();
        }

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void toLogin() {
            startActivity(new Intent(mContext, LoginActivity.class));
            finsh();
        }

        @JavascriptInterface
        public void qrCode() {
            requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                @Override
                public void onGranted() {
                    startActivityForResult(new Intent(mContext, CaptureActivity.class), REQUEST_CODE);
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    for (String permission : deniedPermission) {
                        Toast.makeText(mContext, "被拒绝的权限：" +
                                permission, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static Dialog progressDialog = null;

    public void getBaseUrl(String str, NetworkAdapter adapter) {
        NetWork.getHttp(Api.GetBaseUrl + str, null, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optInt("ret") == 0) {
                        String baseurl = jsonObject.getString("data");
                        adapter.onsuccess(baseurl);
                    } else {
                        ToastUtils.showShortToast("无法解析二维码");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("无法解析二维码");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("无法解析二维码");
            }
        });
    }

}