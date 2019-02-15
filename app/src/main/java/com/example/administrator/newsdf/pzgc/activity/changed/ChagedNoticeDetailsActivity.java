package com.example.administrator.newsdf.pzgc.activity.changed;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import java.util.Map;

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
    private LinearLayout deviceDetailsFunction;
    private TextView deviceDetailsAssign;
    private TextView deviceDetailsUp, titleView;
    private ChagedUtils chagedUtils;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        list = new ArrayList<>();
        chagedUtils = new ChagedUtils();
        Intent intent = getIntent();
        //通知单id
        id = intent.getStringExtra("id");
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(intent.getStringExtra("orgName"));
        /*返回*/
        findViewById(R.id.checklistback).setOnClickListener(this);
        //指派
        deviceDetailsAssign = (TextView) findViewById(R.id.device_details_assign);
        //确认接收
        deviceDetailsUp = (TextView) findViewById(R.id.device_details_assign);
        //底部功能按钮父布局
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        //默认隐藏
        deviceDetailsFunction.setVisibility(View.GONE);
        //内容展示列表
        recycler = (RecyclerView) findViewById(R.id.device_details_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChagedNoticeDetailsAdapter(mContext, list);
        recycler.setAdapter(adapter);
//        Utils.setMargins(recycler, 0, 0, 0, 140);
        adapter.setOnClickListener(new ChagedNoticeDetailsAdapter.OnClickListener() {
            @Override
            public void setproblem(int position) {
                startActivity(new Intent(mContext, ChagedNoticeItemDetailsActivity.class));
            }
        });
        request();
    }

    /*网络请求*/
    private void request() {
        chagedUtils.getNoticeFormMainInfo(id, new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                ArrayList<ChagedNoticeDetails> data = (ArrayList<ChagedNoticeDetails>) map.get("notice");
                list.add(data);

            }

            @Override
            public void onerror(String str) {
                Snackbar.make(titleView, str, Snackbar.LENGTH_LONG).show();
            }
        });
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
