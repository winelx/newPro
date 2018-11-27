package com.example.administrator.newsdf.pzgc.Adapter;

/**
 * @author lx
 * @Created by: 2018/11/27 0027.
 * @description:
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:
 */
public class FmPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private FragmentManager fm;

    public FmPagerAdapter(List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return fragmentList != null && !fragmentList.isEmpty() ? fragmentList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
