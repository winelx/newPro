package com.example.zcjlmodule.ui.activity.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.utils.DialogUtils;
import com.example.zcjlmodule.utils.fragment.FragmentworkUtils;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.BaseDialogUtils;
import measure.jjxx.com.baselibrary.utils.BaseUtils;

public class DetailedlistActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout detailedlistLin;
    private BaseUtils baseUtils;
    private WebView webView;
    private FragmentworkUtils utils;
    private Context mContext;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedlist);
        mContext = this;
        utils = new FragmentworkUtils();
        detailedlistLin = (LinearLayout) findViewById(R.id.detailedlist_lin);
        detailedlistLin.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.detailedlist_webview);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        baseUtils = new BaseUtils();
        boolean status = intent.getBooleanExtra("status", false);
        taskId = intent.getStringExtra("taskId");
        if (status) {
            detailedlistLin.setVisibility(View.VISIBLE);
        } else {
            detailedlistLin.setVisibility(View.GONE);
            baseUtils.setMargins(webView, 0, 0, 0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseUtils != null) {
            baseUtils = null;
        }
    }

    @Override
    public void onClick(View v) {
        //弹出审批框，并返回数据
        BaseDialogUtils.checkandcontent(mContext, new BaseDialogUtils.dialogonclick() {
            @Override
            public void onsuccess(String status, String content) {
                int policy = 0;
                if ("true".equals(status)) {
                    policy = 0;
                } else {
                    policy = 1;
                }
                utils.complateTask(taskId, policy, content);
            }
        });

    }
}
