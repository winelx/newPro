package com.example.administrator.newsdf.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.Aduio_content;
import com.example.administrator.newsdf.Adapter.MoretaskAdapter;
import com.example.administrator.newsdf.R;

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
    ArrayList<Aduio_content> contents;
    ArrayList<String> Dats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moretask);
        mContext = this;
        contents = new ArrayList<>();
        Dats = new ArrayList<>();
        wbsNode = (TextView) findViewById(R.id.wbsnode);
        //任务内容
        mRecyclerView = (RecyclerView) findViewById(R.id.task_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        //添加分割线
        mAdapter = new MoretaskAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        wbsNode.setText("https://www.hao123.com/index.ghttps://www.hao123.com/index.html?tn=96242074_hao_pg");
        contents.add(new Aduio_content("12155", "测试数据", "0", "测试新界面", "历史", "sdg", "未读", "15465498", "1", "1523867262", "测试数所所所", "11", "152386720"));
        for (int i = 0; i < 3; i++) {
            Dats.add("消息" + i);
        }
        mAdapter.getContent(contents, Dats);
    }
}
