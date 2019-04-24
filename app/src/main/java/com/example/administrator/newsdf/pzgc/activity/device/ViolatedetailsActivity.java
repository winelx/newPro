package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.GradeRecyclerAdapter;
import com.example.administrator.newsdf.pzgc.bean.SecstandardlistBean;
import com.example.administrator.newsdf.pzgc.callback.ViolateCallbackUtils;
import com.example.baselibrary.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description: 特种设备违反标准-具体项
 * @Activity：DeviceViolatestandardActivity
 */

public class ViolatedetailsActivity extends BaseActivity {
    private RecyclerView grade_recy;
    private GradeRecyclerAdapter adapter;
    private ArrayList<SecstandardlistBean> list;
    private Context mContext;
    private DeviceUtils deviceUtils;
    private String id;
    private SmartRefreshLayout refreshLayout;
    private TextView titleView;
    private RelativeLayout backNotNull;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);

        mContext = this;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        deviceUtils = new DeviceUtils();
        backNotNull = (RelativeLayout) findViewById(R.id.back_not_null);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        titleView = (TextView) findViewById(R.id.titleView);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setText("违反标准");
        list = new ArrayList<>();
        grade_recy = (RecyclerView) findViewById(R.id.maber_tree);
        grade_recy.setLayoutManager(new LinearLayoutManager(mContext));
        grade_recy.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new GradeRecyclerAdapter(mContext, list);
        grade_recy.setAdapter(adapter);
        adapter.setOnClickListener(new GradeRecyclerAdapter.onClickListener() {
            @Override
            public void onclick(View v, int position) {
                ViolateCallbackUtils.CheckCallback3(
                        list.get(position).getCheck_standard() + "&&&" +
                                list.get(position).getId() + "&&&" +
                                list.get(position).getGroup_id() + "&&&" +
                                list.get(position).getQa_detection_id());
                finish();
            }
        });
        deviceUtils.violateelist(id, new DeviceUtils.ViolickLitenerlist() {
            @Override
            public void onsuccess(ArrayList<SecstandardlistBean> data) {
                list.addAll(data);
                adapter.getData(list);
                if (list.size() > 0) {
                    backNotNull.setVisibility(View.GONE);
                } else {
                    backNotNull.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
