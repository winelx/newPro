package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DeviceDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsProving;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsReply;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsTop;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * @date: 2018/11/28 0028 下午 4:21
 * @description: 特种设备详情页面
 */
public class DeviceDetailsActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private DeviceDetailsAdapter mAdapter;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        list = new ArrayList<>();
        list.add(new DeviceDetailsTop("cesh"));
        list.add(new DeviceDetailsReply("cesh"));
        list.add(new DeviceDetailsProving("cesh"));
        list.add(new DeviceDetailsReply("cesh"));
        list.add(new DeviceDetailsProving("cesh"));
        mRecyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter = new DeviceDetailsAdapter(mContext, list));
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
