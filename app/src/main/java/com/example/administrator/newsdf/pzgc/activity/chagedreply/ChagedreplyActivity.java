package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.fragment.ChagedReplyAllFragment;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.fragment.ChagedReplyMeFragment;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PshooseFragAdapte;
import com.example.baselibrary.view.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：整改回复主界面（承载两个fragment）
 * {@link }
 */
public class ChagedreplyActivity extends BaseActivity implements View.OnClickListener {
    private TextView checkDownAll, checkDownMe, titleView;
    private ViewPager checkDownViewpager;
    private ArrayList<Fragment> mFragment;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkdown_message);
        addActivity(this);
        mFragment = new ArrayList<>();
        //全部回复单
        mFragment.add(new ChagedReplyAllFragment());
        //我的回复单
        mFragment.add(new ChagedReplyMeFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), mFragment);
        checkDownMe = (TextView) findViewById(R.id.check_down_me);
        checkDownAll = (TextView) findViewById(R.id.check_down_all);
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("回复验证");
        checkDownViewpager = (ViewPager) findViewById(R.id.check_down_viewpager);
        checkDownMe.setOnClickListener(this);
        checkDownAll.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        checkDownViewpager.setAdapter(adapter);
        checkDownViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 1;

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
                        all();
                        break;
                    case 1:
                        mine();
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
            case R.id.check_down_me:
                mine();
                break;
            case R.id.check_down_all:
                all();
                break;
            case R.id.checklistback:
                finish();
                break;
            default:
                break;

        }
    }

    private void all() {
        checkDownMe.setTextColor(Color.parseColor("#050505"));
        checkDownAll.setTextColor(Color.parseColor("#5096F8"));
        checkDownViewpager.setCurrentItem(0);
    }

    private void mine() {
        checkDownMe.setTextColor(Color.parseColor("#5096F8"));
        checkDownAll.setTextColor(Color.parseColor("#050505"));
        checkDownViewpager.setCurrentItem(1);
    }
}
