package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.GradeRecyclerAdapter;
import com.example.administrator.newsdf.pzgc.callback.ProblemItemCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * @内容:
 * @date: 2018/12/7 0007 上午 10:56
 */
public class GradeActivity extends BaseActivity {
    private RecyclerView grade_recy;
    private GradeRecyclerAdapter adapter;
    private ArrayList<String> list;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        mContext = this;
        TextView titleView = (TextView) findViewById(R.id.titleView);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setText("隐患等级");
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("隐患等级");
        }
        grade_recy = (RecyclerView) findViewById(R.id.grade_recy);
        grade_recy.setLayoutManager(new LinearLayoutManager(this));
        grade_recy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new GradeRecyclerAdapter(mContext, list);
        grade_recy.setAdapter(adapter);
        adapter.setOnClickListener(new GradeRecyclerAdapter.onClickListener() {
            @Override
            public void onclick(View v, int position) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("grade", list.get(position));
                //设置返回数据
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
