package com.example.administrator.newsdf.pzgc.activity.notice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.network.NetWork;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * @author： lx
 * @创建时间： 2019/5/29 0029 10:24
 * @说明： 通知公告详情
 **/
public class NoticeDetailsFragment extends BaseActivity implements View.OnClickListener {
    private LinearLayout titlel;
    private TextView comTitle;
    private TextView text;
    private WebView mWebView;
    private RelativeLayout linProbar, nonet;
    boolean lean = true;
    private String ids, url;
    private ImageView com_img;
    private Context mContext;
    private ArrayList<FileTypeBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticmessagecheck_web);
        init();
    }

    protected void init() {
        mContext = this;
        list = new ArrayList<>();
        Intent intent = getIntent();
        ids = intent.getStringExtra("ids") + "";
        url = Requests.networks + "admin/sys/sysproclamation/publicdelByApp?id=" + ids;
        //设置标题
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText("公告详情");
        com_img = (ImageView) findViewById(R.id.com_img);
        com_img.setOnClickListener(this);
        text = (TextView) findViewById(R.id.text);
        titlel = (LinearLayout) findViewById(R.id.toolbar_title);
        titlel.setVisibility(View.VISIBLE);
        nonet = (RelativeLayout) findViewById(R.id.nonet);
        nonet.setOnClickListener(this);
        linProbar = (RelativeLayout) findViewById(R.id.lin_probar);
        findViewById(R.id.com_back).setOnClickListener(this);
        mWebView = findViewById(R.id.check);
        sycCook();
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        //允许js
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(false);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.clearCache(true);
        //不使用缓存，只从网络获取数据.
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
        request(ids);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                //返回
                finish();
                break;
            case R.id.nonet:
                linProbar.setVisibility(View.VISIBLE);
                nonet.setVisibility(View.GONE);
                mWebView.loadUrl(url);
                break;
            case R.id.com_img:
                hitask();
                break;
            default:
                break;
        }
    }

    /**
     * 同步cook
     */
    public String getCookie(String tr) {
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(tr);
        return cookieStore.getCookie(httpUrl).toString();
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
        //移除
        cookieManager.removeSessionCookie();
        //同步cookie
        cookieManager.setCookie(Requests.networks, getCookie(Requests.networks));
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }


    /**
     * 显示查看记录
     */
    public void hitask() {
        if (list.size() == 0) {
            ToastUtils.showLongToast("无查看记录");
        } else {
            /**
             * 创建Dialog，参数为当前环境与样式。
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.dialog_list, null);
            ListView listView = (ListView) view.findViewById(R.id.recycler);
            builder.setView(view);
            builder.setCancelable(true);
            SettingAdapter adapter = new SettingAdapter<FileTypeBean>(list, R.layout.taskrecord_item) {
                @Override
                public void bindView(ViewHolder holder, FileTypeBean obj) {
                    holder.setText(R.id.task_cord_data, obj.getUrl().substring(0, 16));
                    holder.setText(R.id.task_cord_name, obj.getName());
                    holder.setText(R.id.task_cord, "查看了" + obj.getType() + "次");
                }
            };
            listView.setAdapter(adapter);
            AlertDialog dialog = builder.create();
            //显示对话框
            dialog.show();
        }

    }

    public void request(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("sysProclamationId", id);
        NetWork.getHttp(Api.GETLOOKRECORDLIST, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject json = data.getJSONObject(i);
                            String time = json.getString("readDate");
                            String readPersonName;
                            try {
                                readPersonName = json.getString("readPersonName");
                            } catch (Exception e) {
                                readPersonName = "";
                            }
                            String readCount = json.getString("readCount");
                            list.add(new FileTypeBean(readPersonName, time, readCount));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }
}
