package com.example.zcjlmodule.ui.activity.approval;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.utils.fragment.ApprovalFragmentUtils;
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
    private ApprovalFragmentUtils fragmentUtils;
    private String approvalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_zc);
        Intent intent = getIntent();
        approvalId = intent.getStringExtra("approvalId");
        fragmentUtils = new ApprovalFragmentUtils();
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.toolbar_icon_title);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.assembly_recycler_smart);
        recyclerView = (EmptyRecyclerView) findViewById(R.id.assembly_recyclerview);
        fragmentUtils.approvalheadcount(approvalId);
        title.setText(intent.getStringExtra("title"));
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_icon_back) {//返回
            finish();

        } else {
        }
    }


}