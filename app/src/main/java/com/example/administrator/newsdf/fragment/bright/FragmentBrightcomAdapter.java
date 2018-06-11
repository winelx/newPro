package com.example.administrator.newsdf.fragment.bright;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.newsdf.bean.work_fr_bright_bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/10 0010.
 * 分公司
 */

public class FragmentBrightcomAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    public static ArrayList<work_fr_bright_bean> mDataCom;
    private boolean doNotifyDataSetChangedOnce = false;

    public FragmentBrightcomAdapter(FragmentManager fm, ArrayList<work_fr_bright_bean> name) {
        super(fm);
        FragmentBrightcomAdapter.mDataCom = name;
    }

    @Override
    public Fragment getItem(int arg0) {
        WorkBrightComFrament fragment = new WorkBrightComFrament(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }
        return mDataCom.size();
    }


    public void getData(ArrayList<work_fr_bright_bean> mData) {
        FragmentBrightcomAdapter.mDataCom = mData;
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
