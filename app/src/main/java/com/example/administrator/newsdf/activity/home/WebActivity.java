package com.example.administrator.newsdf.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.example.administrator.newsdf.R;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * description:  使用webview展示pdf、文档和表格
 *
 * @author lx
 *         date: 2018/3/12 0012 上午 9:15
 *         update: 2018/3/12 0012
 *         version:
 */
public class WebActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbe);
        mWebView = (WebView) findViewById(R.id.web);
        mWebView.loadUrl("https://www.baidu.com/");
        //方式2：加载apk包中的html页面
        //  mWebView.loadUrl("file:///android_asset/test.html");
        //方式3：加载手机本地的html页面
//        mWebView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
        // 方式4： 加载 HTML 页面的一小段内容
        //  WebView.loadData(String data, String mimeType, String encoding);

    }

    @Override
    protected void onStart() {
        super.onStart();
//恢复pauseTimers状态
        mWebView.resumeTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
        mWebView.pauseTimers();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 重写返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
