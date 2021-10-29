package com.example.administrator.newsdf.pzgc.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.base.BaseActivity;


/**
 * 说明：查看附件
 * 创建时间： 2020/12/28 0028 16:01
 * author winelx
 */
public class EnclosurecheckActivity extends BaseActivity {
    private WebView webView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclosurecheck);
        webView = findViewById(R.id.x5webview);
        frameLayout = findViewById(R.id.frameLayout);
        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        //该界面打开更多链接
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        /*  webView.loadUrl(getIntent().getStringExtra("url"));*/
       // openFile(getIntent().getStringExtra("url"));
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        webView.getSettings().setLightTouchEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.clearHistory();
            webView.clearCache(true);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private void openFile(String path) {
     /*   //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", path);
        //临时的路径
        bundle.putString("tempPath", SyncStateContract.Constants.CONTENT_DIRECTORY);
        TbsReaderView readerView = new TbsReaderView(this, (integer, o, o1) -> {
        });
        //加载文件前的初始化工作,加载支持不同格式的插件
        readerView.openFile(bundle);
        // 往容器里添加TbsReaderView控件
        frameLayout.addView(readerView);*/
    }
}
