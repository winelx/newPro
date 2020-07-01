package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.fragment.ExternalProjectType1Fragment;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.fragment.ExternalProjectType2Fragment;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：工程类型
 * 创建时间： 2020/6/30 0030 18:07
 *
 * @author winelx
 */
public class ExternalProjectTypeActivity extends BaseActivity {
    private NoScrollViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task_category);
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new ExternalProjectType1Fragment());
        fragments.add(new ExternalProjectType2Fragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        //设定适配器
        viewpager = (NoScrollViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(adapter);
        LiveDataBus.get().with("project_type", Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        viewpager.setCurrentItem(integer);
                    }
                });
    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int item = viewpager.getCurrentItem();
            if (item == 1) {
                viewpager.setCurrentItem(0);
            } else {
                finish();
            }
            return true;
        }
        return true;
    }
}
