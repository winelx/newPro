package com.example.administrator.newsdf.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.Aduio_content;
import com.example.administrator.newsdf.Adapter.MoretaskAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;

import java.util.ArrayList;

/**
 * description: 多次任务上传界面
 *
 * @author lx
 *         date: 2018/4/16 0016 上午 11:11
 *         update: 2018/4/16 0016
 *         version:
 */
public class MoretaskActivity extends AppCompatActivity {
    private Context mContext;
    private TextView wbsNode;
    private RecyclerView mRecyclerView;
    private MoretaskAdapter mAdapter;
    private ArrayList<Aduio_content> contents;
    private ArrayList<String> Dats;
    private String id, status, wbsid;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moretask);
        mContext = this;
        final Intent intent = getIntent();
        try {
            id = intent.getExtras().getString("frag_id");
            status = intent.getExtras().getString("status");
            wbsid = intent.getExtras().getString("wbsid");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //初始化集合
        initArry();
        //初始化ID
        initfind();
        //初始化数据
        initdata();

        findViewById(R.id.newmoretask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoretaskActivity.this, DirectlyreplyActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void initdata() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        //添加分割线
        mAdapter = new MoretaskAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        wbsNode.setText("https://www.hao123.com/index.ghttps://www.hao123.com/index.html?tn=96242074_hao_pg");
        contents.add(new Aduio_content("12155", "测试数据", "0", "测试新界面", "历史", "sdg", "未读", "15465498", "1", "1523867262", "测试数所所所", "11", "152386720"));
        for (int i = 0; i < 10; i++) {
            Dats.add("部位：基础工程" + i);
        }
        mAdapter.getContent(contents, Dats);
    }

    private void initfind() {
        wbsNode = (TextView) findViewById(R.id.wbsnode);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //任务内容
        mRecyclerView = (RecyclerView) findViewById(R.id.task_content);
    }

    private void initArry() {
        contents = new ArrayList<>();
        Dats = new ArrayList<>();
    }

    public void onclick(int pos) {
        Intent intent = new Intent(mContext, AuditparticularsActivity.class);
        intent.putExtra("frag_id", id);
        intent.putExtra("wbsid", wbsid);
        intent.putExtra("status", status);
        startActivity(intent);
    }
}
