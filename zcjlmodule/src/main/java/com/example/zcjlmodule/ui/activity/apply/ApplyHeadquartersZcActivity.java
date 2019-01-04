package com.example.zcjlmodule.ui.activity.apply;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.zcjlmodule.R;

import com.example.zcjlmodule.callback.NewAddCallback;

import com.example.zcjlmodule.utils.fragment.CurrentApplyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseActivity;

import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * description: 资金申请单指挥部
 *
 * @author lx
 *         date: 2018/11/21 0021 下午 2:
 */
public class ApplyHeadquartersZcActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView recyclerView;
    private String headquarterId,approvalId;
    private CurrentApplyUtils applyUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_zc);
        applyUtils = new CurrentApplyUtils();
        Intent intent = getIntent();
        headquarterId = intent.getStringExtra("headquarterId");
        approvalId = intent.getStringExtra("approvalId");
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.toolbar_icon_title);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.assembly_recycler_smart);
        recyclerView = (EmptyRecyclerView) findViewById(R.id.assembly_recyclerview);
        applyUtils.ApplyHeadCounts(approvalId,headquarterId, new NewAddCallback() {
            @Override
            public void callback(Map<String, String> map) {

            }
        });
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_icon_back) {//返回
            finish();

        } else {
        }
    }


}
