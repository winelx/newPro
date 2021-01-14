package com.example.administrator.fengji.pzgc.activity.check.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.fragment.CheckReportOrgDetailsF;
import com.example.administrator.fengji.pzgc.activity.check.fragment.CheckReportOrgDetailsT;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 统计报表标段扣分得分详情
 *
 * @author lx
 *         date: 2018/8/28 0028 上午 8:47
 *         update: 2018/8/28 0028
 *         version:
 */
public class CheckReportOrgDetailsActivity extends BaseActivity {

    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments;
    private String Id, type, year, mqnum;
    private TextView titleView, tabCheck, tabMessage;

    private static CheckReportOrgDetailsActivity mContext;

    public static CheckReportOrgDetailsActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report_org_details);
        mContext = this;
        titleView = (TextView) findViewById(R.id.titleView);
        tabCheck = (TextView) findViewById(R.id.tab_check);
        tabMessage = (TextView) findViewById(R.id.tab_message);
        tabcheck();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(new CheckReportOrgDetailsT());
        fragments.add(new CheckReportOrgDetailsF());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabcheck();
                mViewPager.setCurrentItem(0);
            }
        });
        tabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabmessage();
                mViewPager.setCurrentItem(1);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > currentPosition) {
                    //右滑
                    currentPosition = position;
                } else if (position < currentPosition) {
                    //左滑
                    currentPosition = position;
                }
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabcheck();
                        break;
                    case 1:
                        tabmessage();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
        mqnum = intent.getStringExtra("mqnum");
        type = intent.getStringExtra("type");
        year = intent.getStringExtra("year");
        titleView.setText(intent.getStringExtra("name"));
    }

    public Map<String, Object> getOrgId() {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", Id);
        map.put("type", type);
        map.put("year", year);
        map.put("mqnum", mqnum);
        return map;
    }

    public void tabcheck() {
        tabCheck.setTextColor(Color.parseColor("#5096F8"));
        tabMessage.setTextColor(Color.parseColor("#050505"));
    }

    public void tabmessage() {
        tabCheck.setTextColor(Color.parseColor("#050505"));
        tabMessage.setTextColor(Color.parseColor("#5096F8"));
    }
}

