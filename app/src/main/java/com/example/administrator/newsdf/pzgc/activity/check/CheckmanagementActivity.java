package com.example.administrator.newsdf.pzgc.activity.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * description: 检查管理_所有有权限的组织类似首页全部界面
 * @author lx
 * date: 2018/8/2 0002 下午 2:39 
 * update: 2018/8/2 0002
 * version: 
*/
public class CheckmanagementActivity extends AppCompatActivity implements View.OnClickListener {
    private ExpandableListView expandableListView;
    private SmartRefreshLayout refreshLayout;
    private TextView comtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagement);
        expandableListView= (ExpandableListView) findViewById(R.id.expandable);
        refreshLayout= (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        comtitle= (TextView) findViewById(R.id.com_title);
        comtitle.setText("检查管理");
        findViewById(R.id.com_back).setOnClickListener(this);
        comtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckmanagementActivity.this,CheckmanagementlistActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.com_back:
                    finish();
                    break;
                default:break;
            }
    }
}
