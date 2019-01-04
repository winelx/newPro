package com.example.zcjlmodule.ui.activity.apply;

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
import com.example.zcjlmodule.ui.activity.HomeZcActivity;
import com.example.zcjlmodule.ui.fragment.apply.AccumulativeApplyFragment;
import com.example.zcjlmodule.ui.fragment.apply.CurrentApplyFragment;
import com.example.zcjlmodule.ui.fragment.apply.ProcedureApplyFragment;
import com.example.zcjlmodule.utils.fragment.CurrentApplyUtils;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.BaseUtils;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:资金申请单
 * @Fragment workFragment
 */

public class ApplyActivityZc extends BaseActivity implements View.OnClickListener {
    private TabLayout mTabLayout;
    private BaseUtils baseUtils;
    private TextView toolbarIconTitle;
    private LinearLayout toolbarIconBack;
    private ViewPager v_selected;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"本期", "累计", "流程"};
    public String status = null, orgId, orgName, applyId;
    private static ApplyActivityZc mContext;
    private CurrentApplyUtils applyUtils;
    private ArrayList<CurrentApplyBean> countlist;
    private ArrayList<PeriodListBean> periodList;
    private ArrayList<FlowListBean> flowList;

    public static ApplyActivityZc getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        countlist = new ArrayList<>();
        periodList = new ArrayList<>();
        flowList = new ArrayList<>();
        Intent intent = getIntent();
        try {
            //是否需要显示审批按钮
            status = intent.getStringExtra("status");
            //组织Id
            orgId = intent.getStringExtra("orgId");
            //单据Id
            applyId = intent.getStringExtra("applyId");
            //组织名称
            orgName = intent.getStringExtra("orgName");
        } catch (Exception e) {
        }
        //帮助类
        applyUtils = new CurrentApplyUtils();
        //帮助类
        baseUtils = new BaseUtils();
        //上下文
        mContext = this;
        //viewpager
        v_selected = (ViewPager) findViewById(R.id.v_selected);
        //缓存3个界面
        v_selected.setOffscreenPageLimit(3);
        //返回键
        toolbarIconBack = (LinearLayout) findViewById(R.id.toolbar_icon_back);
        //标题
        toolbarIconTitle = (TextView) findViewById(R.id.toolbar_icon_title);
        toolbarIconTitle.setText(orgName);
        //tablyout
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //设置tablyout分割线
        baseUtils.addtabpaddingdivider(mTabLayout, mContext);
        //返回点击事件
        toolbarIconBack.setOnClickListener(this);
        applyUtils.applyheadcounts(applyId, new CurrentApplyUtils.OnclickListener() {
            @Override
            public void onSuccess(ArrayList<CurrentApplyBean> List1, ArrayList<PeriodListBean> List2, ArrayList<FlowListBean> List3) {
                //本期
                countlist.addAll(List1);
                //累计
                periodList.addAll(List2);
                //流程
                flowList.addAll(List3);
                //本期
                fragments.add(new CurrentApplyFragment());
                //累计
                fragments.add(new AccumulativeApplyFragment());
                //流程
                fragments.add(new ProcedureApplyFragment());
                //viewpager传递数据
                v_selected.setAdapter(new FmPagerAdapter(fragments, getSupportFragmentManager()));
                //绑定viewpager和tablayout，
                mTabLayout.setupWithViewPager(v_selected);
                //设置tablyout标题，没有使用适配器adapter，减少代码量，直接设置，更加灵活
                for (int j = 0; j < titles.length; j++) {
                    mTabLayout.getTabAt(j).setText(titles[j]);
                }
            }
        });

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

    //获取状态
    public String getstatus() {
        return status;
    }

    //本期
    public ArrayList<CurrentApplyBean> getcountlist() {
        return countlist;
    }

    //累计
    public ArrayList<PeriodListBean> getperiodList() {
        return periodList;
    }

    //流程
    public ArrayList<FlowListBean> getflowList() {
        return flowList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (applyUtils != null) {
            applyUtils = null;
        }
        if (baseUtils != null) {
            baseUtils = null;
        }
    }
}
