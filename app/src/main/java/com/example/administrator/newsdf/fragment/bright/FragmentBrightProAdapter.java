package com.example.administrator.newsdf.fragment.bright;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.newsdf.bean.work_fr_bright_bean;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 * @date 2018/5/10 0010
 * 办公
 */

public class FragmentBrightProAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;
    public static ArrayList<work_fr_bright_bean> mData;

    public FragmentBrightProAdapter(FragmentManager fm, ArrayList<work_fr_bright_bean> name) {
        super(fm);
        FragmentBrightProAdapter.mData = name;
    }

    @Override
    public Fragment getItem(int arg0) {
        WorkBrightProFrament fragment = new WorkBrightProFrament(arg0);
        return fragment;
    }

    @Override
    public int getCount() {

        return mData.size();
    }


    public void getData(ArrayList<work_fr_bright_bean> mData) {
        FragmentBrightProAdapter.mData = mData;
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
