package com.example.administrator.newsdf.pzgc.activity.work.pushadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.example.administrator.newsdf.pzgc.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务下发界面Viewpager的适配器
 * Created by Administrator on 2018/4/9 0009.
 */

public class PushAdapter extends BaseFragmentPagerAdapter {
    public static String Content;
    public ArrayList<String> mData;
    private FragmentManager mFragmentManager;
    private List<String> tagList = new ArrayList<String>();

    public PushAdapter(FragmentManager fm, ArrayList<String> mData) {
        super(fm);
        this.mFragmentManager = fm;
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int arg0) {
        PushFrgment fragment = new PushFrgment(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        LogUtil.i("id", position + "");
        return mData.get(position);
    }

    public void getID(String Content) {
        this.Content = Content;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tagList.add(makeFragmentName(container.getId(), position)); //把tag存起来
        return super.instantiateItem(container, position);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void update(ArrayList<String> datas) {
        this.mData = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}