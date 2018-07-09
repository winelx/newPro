package com.example.administrator.newsdf.pzgc.activity.audit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.audit.fragment.DailyrecordFragment;
import com.example.administrator.newsdf.pzgc.activity.audit.fragment.MonthrecordFragment;
import com.example.administrator.newsdf.pzgc.activity.audit.fragment.QuarterrecordFragment;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PshooseFragAdapte;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * description:审核统计报表
 *
 * @author lx
 *         date: 2018/7/3 0003 上午 9:37
 *         update: 2018/7/3 0003
 *         version:
 */

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager reportViewpager;
    private static ReportActivity mContext;
    private TextView reportDaily, reportMonth, reportQuarter;
    private IconTextView reprot_back;

    public static ReportActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        //初始化控件
        mContext = this;
        findView();
        //初始化数据
        initData();
    }
    private void findView() {
        reprot_back = (IconTextView) findViewById(R.id.reprot_back);
        reportViewpager = (ViewPager) findViewById(R.id.report_viewpager);
        reportDaily = (TextView) findViewById(R.id.report_daily);
        reportMonth = (TextView) findViewById(R.id.report_month);
        reportQuarter = (TextView) findViewById(R.id.report_quarter);
        reportDaily.setOnClickListener(this);
        reportMonth.setOnClickListener(this);
        reportQuarter.setOnClickListener(this);
        reprot_back.setOnClickListener(this);
    }

    private void initData() {
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new DailyrecordFragment());
        fragments.add(new MonthrecordFragment());
        fragments.add(new QuarterrecordFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        reportViewpager.setAdapter(adapter);
        //缓存界面
        reportViewpager.setOffscreenPageLimit(3);
        reportViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        Daily();
                        break;
                    case 1:
                        Month();
                        break;
                    case 2:
                        Quarter();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_daily:
                reportViewpager.setCurrentItem(0, false);
                break;
            case R.id.report_month:
                reportViewpager.setCurrentItem(1, false);
                break;
            case R.id.report_quarter:
                reportViewpager.setCurrentItem(2, false);
                break;
            case R.id.reprot_back:
                finish();
                break;
            default:
                break;
        }
    }

    public void Daily() {
        reportDaily.setTextColor(Color.parseColor("#5096F8"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    public void Month() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#5096F8"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    public void Quarter() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#5096F8"));
    }


}
