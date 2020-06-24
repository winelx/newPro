package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.CheckListAdapter;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.baselibrary.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：外业检查
 * 创建时间： 2020/6/23 0023 14:20
 *
 * @author winelx
 */
public class ExternalCheckActiviy extends BaseActivity implements View.OnClickListener {
    private LinearLayout comBack;
    private ExpandableListView expandable;
    private SmartRefreshLayout refreshLayout;
    private CheckListAdapter mAdapter;
    private ArrayList<String> list;
    private Map<String, List<Home_item>> map;
    private List<Home_item> mData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_check_external);
        mContext = this;
        list = new ArrayList<>();
        list.add("测试");
        mData = new ArrayList<>();
        mData.add(new Home_item("14", "14", "14", "14", "14", "14", "14", "4", "4", true));
        map = new HashMap<>();
        map.put("测试", mData);
        comBack = findViewById(R.id.com_back);
        comBack.setOnClickListener(this);
        expandable = findViewById(R.id.expandable);
        refreshLayout = findViewById(R.id.refreshlayout);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.finishRefresh(true);
        mAdapter = new CheckListAdapter(list, map, mContext);
        expandable.setAdapter(mAdapter);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                refreshlayout.finishRefresh(true);
            }
        });
        expandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String ids = map.get(list.get(groupPosition)).get(childPosition).getId();
                String orgname = map.get(list.get(groupPosition)).get(childPosition).getOrgname();
                Intent intent = new Intent(mContext, ExternalCheckListActiviy.class);
                intent.putExtra("orgid", ids);
                intent.putExtra("orgName", orgname);
                startActivity(intent);
                map.get(list.get(groupPosition)).get(childPosition).setUnfinish("0");
                expandable.setAdapter(mAdapter);
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.com_back) {

        }
    }
}
