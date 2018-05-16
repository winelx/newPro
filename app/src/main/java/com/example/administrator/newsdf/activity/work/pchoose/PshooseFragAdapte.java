package com.example.administrator.newsdf.activity.work.pchoose;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * description: 图册viewpager的适配器
 *
 * @author lx
 *         date: 2018/5/16 0016 下午 4:02
 *         update: 2018/5/16 0016
 *         version:
 */
public class PshooseFragAdapte extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public PshooseFragAdapte(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }

}
