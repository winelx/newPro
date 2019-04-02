package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.FmPagerAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.fragment.DeviceAllFragment;
import com.example.administrator.newsdf.pzgc.activity.device.fragment.DeviceMeFragment;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @description: 特种设备界面
 * @date: 2018/11/27 0027 上午 11:32
 */
public class DeviceActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"全部", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        addActivity(this);
        context = this;
        //tablyout
        tabLayout = (TabLayout) findViewById(R.id.device_tablayout);
        //viewpager
        viewPager = (ViewPager) findViewById(R.id.device_viewPager);
        //标题
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("特种设备整改");
        //返回键
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置tablyout 分割线
        Utils.addtabpaddingdivider(tabLayout, context);
        //调加界面
        fragments.add(new DeviceAllFragment());
        fragments.add(new DeviceMeFragment());
        //viewpager传递数据
        viewPager.setAdapter(new FmPagerAdapter(fragments, getSupportFragmentManager()));
        //绑定viewpager和tablayout，
        tabLayout.setupWithViewPager(viewPager);
        //设置tablyout标题，没有使用适配器adapter，减少代码量，直接设置，更加灵活
        for (int j = 0; j < titles.length; j++) {
            tabLayout.getTabAt(j).setText(titles[j]);
        }
        //新增数据
        findViewById(R.id.device_newadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceActivity.this, NewDeviceActivity.class));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

}
