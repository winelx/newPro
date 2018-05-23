package com.example.administrator.newsdf.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.newsdf.activity.work.BridhtFragment;
import com.example.administrator.newsdf.camera.ToastUtils;

import java.util.ArrayList;

/**
 * 亮点展示界面的viewpager的适配器
 * Created by Administrator on 2018/4/20 0020.
 */

public class BrightViewpagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;
    public static ArrayList<String> mData;

    public BrightViewpagerAdapter(FragmentManager fm, ArrayList<String> name) {
        super(fm);
        this.mFragmentManager = fm;
        mData = name;

    }

    @Override
    public Fragment getItem(int arg0) {
        ToastUtils.showLongToast(""+arg0);
        BridhtFragment fragment = new BridhtFragment(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }


    public void getData(ArrayList<String> mData) {
        BrightViewpagerAdapter.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
