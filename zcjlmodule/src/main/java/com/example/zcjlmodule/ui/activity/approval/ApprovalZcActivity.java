package com.example.zcjlmodule.ui.activity.approval;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.FmPagerAdapter;

import com.example.zcjlmodule.ui.fragment.approval.AccumulativeApprovalFragment;
import com.example.zcjlmodule.ui.fragment.approval.CurrentApprovalFragment;
import com.example.zcjlmodule.ui.fragment.approval.ProcedureApprovalFragment;

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
    private Context mContext;
    private TextView toolbarIconTitle;
    private LinearLayout toolbarIconBack;
    private ViewPager v_selected;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"本期", "累计", "流程"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
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
        //tablyout
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //设置tablyout分割线
        baseUtils.addtabpaddingdivider(mTabLayout, mContext);
        //返回点击事件
        toolbarIconBack.setOnClickListener(this);
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
