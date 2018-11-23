package com.example.zcjlmodule.ui.activity.apply;

import android.content.Context;
import android.support.design.widget.TabLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.FmPagerAdapter;
import com.example.zcjlmodule.ui.fragment.apply.AccumulativeApplyFragment;
import com.example.zcjlmodule.ui.fragment.apply.CurrentApplyFragment;
import com.example.zcjlmodule.ui.fragment.apply.ProcedureApplyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.BaseUtils;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * description: 资金申请单
 *
 * @author lx
 *         date: 2018/11/21 0021 下午 2:
 */
public class HeadquartersZcActivity extends BaseActivity implements View.OnClickListener {
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
