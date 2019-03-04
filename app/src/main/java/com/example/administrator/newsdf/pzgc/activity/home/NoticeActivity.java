package com.example.administrator.newsdf.pzgc.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.NoticeAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.NoticedBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.baselibrary.EmptyRecyclerView;
import com.example.baselibrary.EmptyUtils;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：首页通知任务展示列表
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {
    private EmptyRecyclerView recycler;
    private TextView comTitle;
    private EmptyUtils emptyUtils;
    private NoticeAdapter mAdapter;

    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Intent intent = getIntent();
        list = new ArrayList<>();
        list.add(new NoticedBean("4545"));
        list.add(new NoticedBean("4545"));
        emptyUtils = new EmptyUtils(this);
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText(intent.getStringExtra("title"));
        findViewById(R.id.com_back).setOnClickListener(this);
        recycler = (EmptyRecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setEmptyView(emptyUtils.init());
        mAdapter = new NoticeAdapter(list);
        recycler.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
