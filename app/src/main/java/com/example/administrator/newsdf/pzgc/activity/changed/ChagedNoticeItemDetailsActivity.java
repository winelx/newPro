package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：整改通知单具体项详情
 * {@link }
 */

public class ChagedNoticeItemDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ChagedNoticeItemDetailsAdapter mAdapter;
    private SmartRefreshLayout refreshlayout;
    private ArrayList<Object> list;
    private Context mContext;
    private TextView titleView;
    private String id;
    private ChagedUtils chagedUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        chagedUtils = new ChagedUtils();
        list = new ArrayList<>();
        mContext = this;
        findViewById(R.id.checklistback).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.maber_tree);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        mAdapter = new ChagedNoticeItemDetailsAdapter(list, mContext);
        recyclerView.setAdapter(mAdapter);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("查看详情");
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            default:
                break;
        }
    }

    private void request() {
        /*通知单非保存状态时查询单个问题项详情*/
        chagedUtils.getNoticeDetailsInfo(id, new ChagedUtils.NoticeFormMainInfoCallback() {
            @Override
            public void onsuccess(ArrayList<Object> data) {
                list.addAll(data);
                mAdapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {

            }
        });
    }
}
