package com.example.zcjlmodule.ui.activity.approval;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/23 0023.
 * @description:资金审批单指挥部
 */

public class ApprovalHeadquartersZcActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_zc);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.toolbar_icon_title);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.assembly_recycler_smart);
        recyclerView = (EmptyRecyclerView) findViewById(R.id.assembly_recyclerview);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_icon_back:
                //返回
                finish();
                break;
            default:
                break;
        }
    }


}