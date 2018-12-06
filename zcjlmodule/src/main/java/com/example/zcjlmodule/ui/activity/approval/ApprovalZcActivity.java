package com.example.zcjlmodule.ui.activity.approval;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.FmPagerAdapter;

import com.example.zcjlmodule.bean.CurrentApplyBean;
import com.example.zcjlmodule.bean.FlowListBean;
import com.example.zcjlmodule.bean.PeriodListBean;
import com.example.zcjlmodule.ui.activity.apply.ApplyActivityZc;
import com.example.zcjlmodule.ui.fragment.approval.AccumulativeApprovalFragment;
import com.example.zcjlmodule.ui.fragment.approval.CurrentApprovalFragment;
import com.example.zcjlmodule.ui.fragment.approval.ProcedureApprovalFragment;
import com.example.zcjlmodule.utils.fragment.CurrentApplyUtils;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.BaseUtils;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:资金审批单
 */

public class ApprovalZcActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout mTabLayout;
    private BaseUtils baseUtils;
    private TextView toolbarIconTitle;
    private LinearLayout toolbarIconBack;
    private ViewPager v_selected;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"本期", "累计", "流程"};
    private static ApprovalZcActivity mContext;

    public static ApprovalZcActivity getInstance() {
        return mContext;
    }

    private String status = null, orgId, orgName, approvalId;
    private CurrentApplyUtils applyUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        //帮助类
        baseUtils = new BaseUtils();
        applyUtils = new CurrentApplyUtils();
        //上下文
        mContext = this;
        //初始化控件
        findId();
        Intent intent = getIntent();
        try {
            //单据Id
            approvalId = intent.getStringExtra("approvalId");
            //组织Id
            orgId = intent.getStringExtra("orgId");
            //组织名称
            orgName = intent.getStringExtra("orgName");
            //状态（false 隐藏控件；空：不隐藏）
            status = intent.getStringExtra("status");

        } catch (Exception e) {
        }
        toolbarIconTitle.setText(orgName);
        //设置tablyout分割线
        baseUtils.addtabpaddingdivider(mTabLayout, mContext);
        //返回点击事件
        toolbarIconBack.setOnClickListener(this);
        //缓存3个界面
        v_selected.setOffscreenPageLimit(3);
        /*
           将三个界面存在list中，
         */
        //本期
        fragments.add(new CurrentApprovalFragment());
        //累计
        fragments.add(new AccumulativeApprovalFragment());
        //流程
        fragments.add(new ProcedureApprovalFragment());
        //viewpager传递数据
        v_selected.setAdapter(new FmPagerAdapter(fragments, getSupportFragmentManager()));
        //绑定viewpager和tablayout，
        mTabLayout.setupWithViewPager(v_selected);
        //设置tablyout标题，没有使用适配器adapter，减少代码量，直接设置，更加灵活
        for (int j = 0; j < titles.length; j++) {
            mTabLayout.getTabAt(j).setText(titles[j]);
        }
        applyUtils.approvalheadcounts(approvalId, new CurrentApplyUtils.OnclickListener() {
            @Override
            public void onSuccess(ArrayList<CurrentApplyBean> data, ArrayList<PeriodListBean> periodList, ArrayList<FlowListBean> flowLists) {

            }
        });

    }

    /**
     * 初始化控件
     */
    private void findId() {
        //viewpager
        v_selected = (ViewPager) findViewById(R.id.v_selected);
        //返回键
        toolbarIconBack = (LinearLayout) findViewById(R.id.toolbar_icon_back);
        //标题
        toolbarIconTitle = (TextView) findViewById(R.id.toolbar_icon_title);
        //tablyout
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
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

    public String getstatus() {
        return status;
    }

}
