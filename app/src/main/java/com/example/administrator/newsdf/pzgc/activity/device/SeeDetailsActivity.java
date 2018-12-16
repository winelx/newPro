package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SeeDetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceDetailsUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:查看整改问题项详情界面
 */

public class SeeDetailsActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Object> list;
    private DeviceDetailsUtils detailsUtils;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        Dates.getDialogs(this,"请求数据中...");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mContext = this;
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText(intent.getStringExtra("orgname"));
        detailsUtils = new DeviceDetailsUtils();
        list = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        mRecyclerView.addItemDecoration(divider1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detailsUtils.seedetails(id, new DeviceDetailsUtils.SeeDetailslitener() {
            @Override
            public void onsuccess(ArrayList<Object> lists) {
                list.addAll(lists);
                mRecyclerView.setAdapter(new SeeDetailsAdapter(mContext, list));
            }
        });
    }
}
