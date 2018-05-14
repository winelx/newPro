package com.example.administrator.newsdf.fragment;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.newsdf.bean.work_fr_bright_bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/10 0010.
 * 办公
 */

public class FragmentBrightAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    public static ArrayList<work_fr_bright_bean> mData;


    public FragmentBrightAdapter(FragmentManager fm, ArrayList<work_fr_bright_bean> name) {
        super(fm);
        this.mData = name;
    }

    @Override
    public Fragment getItem(int arg0) {
        WorkBrightFrament fragment = new WorkBrightFrament(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getId();
    }


    public void getData(ArrayList<work_fr_bright_bean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
}
