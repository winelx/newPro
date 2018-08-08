package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

/**
 * description: 检查通知模块通知列表
 *
 * @author lx
 *         date: 2018/8/8 0008 下午 4:03
 *         update: 2018/8/8 0008
 *         version:
 */
public class ChecknoticeMessagelistActivity extends AppCompatActivity {
    private SettingAdapter adapter;
    private ListView listView;
    ArrayList<String> mData;
    private Context mContext;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmb_tree);
        listView = (ListView) findViewById(R.id.maber_tree);
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("通知管理");
        listView.setDividerHeight(0);
        mContext = ChecknoticeMessagelistActivity.this;
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("" + i);
        }
        adapter = new SettingAdapter<String>(mData, R.layout.check_notice_message) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.management_title, obj);
            }
        };
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nullposion));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, IssuedTaskDetailsActivity.class));
            }
        });
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
