package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.AssignhistoryAcapter;
import com.example.administrator.newsdf.pzgc.bean.DeviceRecordBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/21 0021}
 * 描述：指派记录
 * {@link }
 */
public class AssignhistoryActivity extends BaseActivity {
    private SmartRefreshLayout refreshlayout;
    private AssignhistoryAcapter mAdapter;
    private ArrayList<DeviceRecordBean> list;
    private RecyclerView recyclerView;
    private ChagedUtils chagedUtils;
    private String noticeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);
        Intent intent = getIntent();
        noticeId = intent.getStringExtra("noticeId");
        list = new ArrayList<>();
        chagedUtils = new ChagedUtils();
        recyclerView = (RecyclerView) findViewById(R.id.maber_tree);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        mAdapter = new AssignhistoryAcapter(R.layout.taskrecord_item, list);
        recyclerView.setAdapter(mAdapter);
        chagedUtils.assignhistory("", new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
//                list.addAll(ArrayList < DeviceRecordBean > map.get("list"));
//                mAdapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {

            }
        });
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
