package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CorrectReplyAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:整改回复（详情页编辑）
 * @Activity：
 */

public class CorrectReplyActivity extends BaseActivity {
    private CorrectReplyAdapter mAdapter;
    private ArrayList<String> list;
    private Context mContext;
    private RecyclerView device_details_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        device_details_recycler = (RecyclerView) findViewById(R.id.device_details_recycler);
        device_details_recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("sss");
        }
        mAdapter = new CorrectReplyAdapter(list, mContext);
        device_details_recycler.setAdapter(mAdapter);
    }
}
