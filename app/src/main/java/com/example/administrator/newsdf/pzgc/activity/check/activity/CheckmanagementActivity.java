package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckManagementAdapter;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.fragment.presenter.AllmessagePer;
import com.example.administrator.newsdf.pzgc.fragment.view.UiAllMessageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;
import java.util.Map;

import static com.example.administrator.newsdf.R.id.expandable;

/**
 * description: 检查管理_所有有权限的组织列表
 * @author lx
 * date: 2018/8/2 0002 下午 2:39 
 * update: 2018/8/2 0002
 * version: 
*/
public class CheckmanagementActivity extends AppCompatActivity implements View.OnClickListener,UiAllMessageView {
    private ExpandableListView expandableListView;
    private SmartRefreshLayout refreshLayout;
    private TextView comtitle;
    private CheckManagementAdapter mAdapter;
    private View.OnClickListener ivGoToChildClickListener;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagement);
        mContext=CheckmanagementActivity.this;
        expandableListView= (ExpandableListView) findViewById(expandable);
        refreshLayout= (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        comtitle= (TextView) findViewById(R.id.titleView);
        comtitle.setText("检查管理");
        findViewById(R.id.checklistback).setOnClickListener(this);

        Intent();
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Intent();
                refreshlayout.finishRefresh(800);
            }
        });
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.checklistback:
                    finish();
                    break;
                default:break;
            }
    }

    @Override
    public void setAdapter(List<String> list, Map<String, List<Home_item>> map) {
        mAdapter = new CheckManagementAdapter(list, map, mContext,
                ivGoToChildClickListener);
        expandableListView.setAdapter(mAdapter);
        refreshLayout.finishRefresh(true);
    }
    //重新加载数据
    public void Intent() {
        new AllmessagePer(this).getMode();
    }
}
