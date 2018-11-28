package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DeviceDetailsAdapter;

/**
 * @author lx
 * @date: 2018/11/28 0028 下午 4:21
 * @description: 特种设备详情页面
 */
public class DeviceDetailsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private DeviceDetailsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mRecyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }
}
