package com.example.zcjlmodule.ui.activity.approval;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.FmPagerAdapter;
import com.example.zcjlmodule.ui.fragment.apply.CapitalApprovalFFragment;
import com.example.zcjlmodule.ui.fragment.apply.CapitalApprovalTFragment;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.BaseUtils;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:资金拨付申请
 */

public class CapitalApprovalZcActivity extends BaseActivity implements View.OnClickListener {
    private String[] titles = {"未审批", "已审批"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BaseUtils baseUtils;
    private Context mContext;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseUtils = new BaseUtils();
        mContext = this;
        setContentView(R.layout.activity_capital_approval_zc);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.capital_tablayout);
        viewPager = (ViewPager) findViewById(R.id.capital_viewpager);
        //设置tablyout分割线
        baseUtils.addtabpaddingdivider(tabLayout, mContext);
        fragments.add(new CapitalApprovalTFragment());
        fragments.add(new CapitalApprovalFFragment());
        //viewpager传递数据
        viewPager.setAdapter(new FmPagerAdapter(fragments, getSupportFragmentManager()));
        //绑定viewpager和tablayout，
        tabLayout.setupWithViewPager(viewPager);
        //设置tablyout标题，没有使用适配器adapter，减少代码量，直接设置，更加灵活
        for (int j = 0; j < titles.length; j++) {
            tabLayout.getTabAt(j).setText(titles[j]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_icon_back:
                finish();
                break;
            default:
                break;
        }
    }
}
