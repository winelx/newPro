package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SeeDetailsAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:查看详情
 */

public class SeeDetailsActivity extends BaseActivity {
    private SeeDetailsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        list = new ArrayList<>();
        setContentView(R.layout.activity_device_details);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setAdapter(new SeeDetailsAdapter(mContext, list));
    }
}
