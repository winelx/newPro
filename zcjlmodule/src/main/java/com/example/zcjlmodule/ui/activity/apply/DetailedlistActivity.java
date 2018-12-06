package com.example.zcjlmodule.ui.activity.apply;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.zcjlmodule.R;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.BaseUtils;

public class DetailedlistActivity extends BaseActivity {
    private LinearLayout detailedlistLin;
    private BaseUtils baseUtils;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedlist);
        detailedlistLin = (LinearLayout) findViewById(R.id.detailedlist_lin);
        webView = (WebView) findViewById(R.id.detailedlist_webview);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
        Intent intent = getIntent();
        baseUtils = new BaseUtils();
        boolean status = intent.getBooleanExtra("status", false);
        if (status) {
            detailedlistLin.setVisibility(View.VISIBLE);
        } else {
            detailedlistLin.setVisibility(View.GONE);
            baseUtils.setMargins(webView, 0, 0, 0, 0);
        }
    }
}
