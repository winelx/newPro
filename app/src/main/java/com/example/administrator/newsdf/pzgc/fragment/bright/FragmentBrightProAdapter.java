package com.example.administrator.newsdf.pzgc.fragment.bright;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.newsdf.pzgc.bean.work_fr_bright_bean;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2018/5/10 0010
 * 项目
 */

public class FragmentBrightProAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    public static ArrayList<work_fr_bright_bean> mDataPro;
    private boolean doNotifyDataSetChangedOnce = false;

    public FragmentBrightProAdapter(FragmentManager fm, ArrayList<work_fr_bright_bean> name) {
        super(fm);
        FragmentBrightProAdapter.mDataPro = name;
    }

    @Override
    public Fragment getItem(int arg0) {
        WorkBrightProFrament fragment = new WorkBrightProFrament(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }
        return mDataPro.size();
    }


    public void getData(ArrayList<work_fr_bright_bean> mData) {
        FragmentBrightProAdapter.mDataPro = mData;
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
