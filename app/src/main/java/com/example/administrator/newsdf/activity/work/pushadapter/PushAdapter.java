package com.example.administrator.newsdf.activity.work.pushadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.example.administrator.newsdf.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：winelx
 * 时间：2017/12/2 0002:下午 16:49
 * 说明：
 */
public class PushAdapter extends BaseFragmentPagerAdapter {
    public static ArrayList<String> mData;
    public static String Content;
    private FragmentManager mFragmentManager;
    private List<String> tagList = new ArrayList<String>();
    public PushAdapter(FragmentManager fm, ArrayList<String> mData) {
        super(fm);
        PushAdapter.mData = mData;
        this.mFragmentManager = fm;

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
        PushAdapter.Content = Content;

    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //把tag存起来
        tagList.add(makeFragmentName(container.getId(), getItemId(position)));
        return super.instantiateItem(container, position);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void update(ArrayList<String> datas){
        mData = datas;
        notifyDataSetChanged();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}