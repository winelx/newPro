package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.CheckMonthQuarterFragment;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.CheckMonthReportFragment;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.CheckMonthYearFragment;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PshooseFragAdapte;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 检查审核报表
 *
 * @author lx
 *         date: 2018/8/10 0010 上午 10:19
 *         update: 2018/8/10 0010
 *         version:
 */
public class CheckReportActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView reportDaily, reportMonth, reportQuarter;
    private ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);
        TextView textView = (TextView) findViewById(R.id.titleView);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        reportDaily = (TextView) findViewById(R.id.report_daily);
        reportMonth = (TextView) findViewById(R.id.report_month);
        reportQuarter = (TextView) findViewById(R.id.report_quarter);
        reportDaily.setOnClickListener(this);
        reportMonth.setOnClickListener(this);
        reportQuarter.setOnClickListener(this);
        textView.setText("检查报表");
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new CheckMonthQuarterFragment());
        fragments.add(new CheckMonthReportFragment());
        fragments.add(new CheckMonthYearFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        //缓存界面
        viewpager.setOffscreenPageLimit(3);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        daily();
                        break;
                    case 1:
                        month();
                        break;
                    case 2:
                        quarter();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 月控件不同状态显示控制
     */
    public void daily() {
        reportDaily.setTextColor(Color.parseColor("#5096F8"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    /**
     * 季度控件不同状态显示控制
     */
    public void month() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#5096F8"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    /**
     * 年控件不同状态显示控制
     */
    public void quarter() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#5096F8"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_daily:
                //月
                break;
            case R.id.report_month:
                //季度
                break;
            case R.id.report_quarter:
                //面
                break;
            default:
                break;
        }
    }
}
