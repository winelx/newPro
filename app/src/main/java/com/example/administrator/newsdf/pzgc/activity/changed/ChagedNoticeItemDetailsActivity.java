package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsChaged;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsProblem;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsRecord;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);
        list = new ArrayList<>();
        list.add(new NoticeItemDetailsProblem("1212"));
        list.add(new NoticeItemDetailsChaged("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));
        list.add(new NoticeItemDetailsRecord("1212"));

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
}
