package com.example.administrator.newsdf.pzgc.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import android.view.View;

import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.NoticeAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.NoticedBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.baselibrary.EmptyRecyclerView;
import com.example.baselibrary.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：首页通知任务展示列表
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {
    private EmptyRecyclerView recycler;
    private TextView comTitle;
    private EmptyUtils emptyUtils;
    private NoticeAdapter mAdapter;
    private SmartRefreshLayout refreshLayout;
    private ArrayList<Object> list;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mContext = this;
        Intent intent = getIntent();
        list = new ArrayList<>();
        list.add(new NoticedBean("4545"));
        list.add(new NoticedBean("4545"));
        emptyUtils = new EmptyUtils(this);
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText(intent.getStringExtra("title"));
        findViewById(R.id.com_back).setOnClickListener(this);
        recycler = (EmptyRecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recycler.setLayoutManager(layoutManager);
        recycler.setEmptyView(emptyUtils.init());
        mAdapter = new NoticeAdapter(list);
        recycler.setAdapter(mAdapter);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
