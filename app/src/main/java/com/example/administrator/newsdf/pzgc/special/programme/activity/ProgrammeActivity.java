package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.fragment.AllOrgFragment;
import com.example.administrator.newsdf.pzgc.special.loedger.fragment.MineOrgFragment;
import com.example.administrator.newsdf.pzgc.special.programme.fragment.ProAllOrgFragment;
import com.example.administrator.newsdf.pzgc.special.programme.fragment.ProMineOrgFragment;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 13:34
 * @说明 专项施工方案管理
 **/

@SuppressLint("Registered")
public class ProgrammeActivity extends BaseActivity implements View.OnClickListener {
    private TextView checkDownAll, checkDownMe, titleView;
    private ViewPager checkDownViewpager;
    private ArrayList<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkdown_message);
        mFragment = new ArrayList<>();
        mFragment.add(new ProAllOrgFragment());
        mFragment.add(new ProMineOrgFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), mFragment);
        checkDownMe = (TextView) findViewById(R.id.check_down_me);
        checkDownAll = (TextView) findViewById(R.id.check_down_all);
        titleView = (TextView) findViewById(R.id.com_title);
        titleView.setText("专项施工方案管理");
        checkDownViewpager = (ViewPager) findViewById(R.id.check_down_viewpager);
        checkDownMe.setOnClickListener(this);
        checkDownAll.setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
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
            case R.id.com_back:
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
