package com.example.administrator.newsdf.pzgc.activity.changed;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：下发整改通知详情
 * {@link }
 */
public class ChagedNoticeDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycler;
    private ChagedNoticeDetailsAdapter adapter;
    private Context mContext;
    private ArrayList<Object> list;
    private LinearLayout device_details_function;

    private TextView device_details_assign;
    private TextView device_details_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        list = new ArrayList<>();
        list.add(new ChagedNoticeDetails("111"));
        list.add(new ChagedNoticeDetailslsit("111"));
        list.add(new ChagedNoticeDetailslsit("111"));
        for (int i = 0; i < 20; i++) {
            list.add(new ChagedNoticeDetailslsit("111"));
        }
        findViewById(R.id.checklistback).setOnClickListener(this);
        //指派
        device_details_assign = (TextView) findViewById(R.id.device_details_assign);
        //确认接收
        device_details_up = (TextView) findViewById(R.id.device_details_assign);
        device_details_function = (LinearLayout) findViewById(R.id.device_details_function);
        recycler = (RecyclerView) findViewById(R.id.device_details_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChagedNoticeDetailsAdapter(mContext, list);
        recycler.setAdapter(adapter);
        device_details_function.setVisibility(View.VISIBLE);
        Utils.setMargins(recycler, 0, 0, 0, 90);
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
