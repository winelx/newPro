package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.CheckitemAdapter;
import com.example.administrator.newsdf.pzgc.bean.Checkitem;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：检查项
 * {@link }
 */
public class CheckitemActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView inspect_content;
    private CheckitemAdapter mAdapter;
    private ArrayList<Object> list;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_itemlist);
        mContext = this;
        list = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(this);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list.add("标题");
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add("标题");
        list.add(new Checkitem("测试数据", true));
        list.add(new Checkitem("测试数据", true));
        list.add(new Checkitem("测试数据", false));
        list.add("标题");
        list.add(new Checkitem("测试数据", true));
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        mAdapter = new CheckitemAdapter(list);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
