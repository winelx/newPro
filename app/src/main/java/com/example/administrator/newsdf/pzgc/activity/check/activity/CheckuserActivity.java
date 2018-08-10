package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckUserListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on
 */
/**
 * description: 检查模块——下发通知的选择联系人
 * @author lx
 * date: 2018/8/8 0008 上午 9:37
 * update: 2018/8/7 0007
 * version:
*/

public class CheckuserActivity extends AppCompatActivity implements View.OnClickListener {
    private ExpandableListView expandableListView;
    private SmartRefreshLayout refreshLayout;
    private TextView comtitle;
    private CheckUserListAdapter adapter;
    private View.OnClickListener ivGoToChildClickListener;
    private int groupPosition = 0;
    private Context mContext;
    private ArrayList<String> list;
    private Map<String, List<String>> map;
    private LinearLayout checklistmeun;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagement);
        mContext = CheckuserActivity.this;
        list = new ArrayList<>();
        list.add("项目经理");
        list.add("技术员");
        list.add("实施人员");
        list.add("工区长");
        map = new HashMap<>();
        map.put("项目经理", list);
        map.put("技术员", list);
        map.put("实施人员", list);
        map.put("工区长", list);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        TextView checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setOnClickListener(this);
        checklistmeuntext.setVisibility(View.VISIBLE);
//        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.expandable);
        comtitle = (TextView) findViewById(R.id.titleView);
        comtitle.setText("选择责任人");
        findViewById(R.id.checklistback).setOnClickListener(this);
        adapter = new CheckUserListAdapter(list, map, mContext,
                ivGoToChildClickListener);
        expandableListView.setAdapter(adapter);
        ivGoToChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取被点击图标所在的group的索引
                Map<String, Object> map = (Map<String, Object>) v.getTag();
                groupPosition = (int) map.get("groupPosition");
                //判断分组是否展开
                boolean isExpand = expandableListView.isGroupExpanded(groupPosition);
                if (isExpand) {
                    //收缩
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    //展开
                    expandableListView.expandGroup(groupPosition);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.checklistmeuntext:
                ToastUtils.showLongToast("保存");
                break;
            default:
                break;
        }
    }

    public void getdata(String name) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        setResult(3, intent);
        finish();
    }
}
