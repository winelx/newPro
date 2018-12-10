package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DeviceDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsProving;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsReply;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsTop;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;

/**
 * @author lx
 * @date: 2018/11/28 0028 下午 4:21
 * @description: 特种设备详情页面
 */
public class DeviceDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private DeviceDetailsAdapter mAdapter;
    private ArrayList<Object> list;
    private LinearLayout deviceDetailsFunction;
    private TextView deviceDetailsDown, deviceDetailsProving, deviceDetailsEdit;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        //帮助类（RecyclerView需要根据状态改变margin）
        utils = new Utils();
        list = new ArrayList<>();
        //编辑
        deviceDetailsEdit = (TextView) findViewById(R.id.device_details_edit);
        deviceDetailsEdit.setOnClickListener(this);
        //验证
        deviceDetailsProving = (TextView) findViewById(R.id.device_details_proving);
        deviceDetailsProving.setOnClickListener(this);
        //下发
        deviceDetailsDown = (TextView) findViewById(R.id.device_details_down);
        deviceDetailsDown.setOnClickListener(this);
        //功能
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        //recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter = new DeviceDetailsAdapter(mContext, list));
        findViewById(R.id.checklistback).setOnClickListener(this);
        //设置控件的margin值
        utils.setMargins(mRecyclerView, 0, 0, 0, 120);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_details_proving:
                startActivity(new Intent(mContext, VerificationActivity.class));
                //验证
                break;
            case R.id.device_details_edit:
                startActivity(new Intent(mContext, CorrectReplyActivity.class));
                //编辑
                break;
            case R.id.device_details_down:
                //下发
                break;
            case R.id.checklistback:
                //返回
                finish();
                break;
            default:
                break;
        }
    }
}
