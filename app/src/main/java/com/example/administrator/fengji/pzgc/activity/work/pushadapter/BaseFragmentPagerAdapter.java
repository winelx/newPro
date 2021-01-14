package com.example.administrator.fengji.pzgc.activity.work.pushadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽出的viewpager的基类
 * Created by Administrator on 2018/4/9 0009.
 */

public abstract class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;
    private List<String> tagList ;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
        tagList  = new ArrayList<String>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tagList.add(makeFragmentName(container.getId(), position));
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        tagList.remove(makeFragmentName(container.getId(), position));
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void update(int position) {
        Fragment fragment = (Fragment) mFragmentManager.findFragmentByTag(tagList.get(position));
        if (fragment == null) {
            return;
        }
        if (fragment instanceof UpdateAble) {//这里唯一的要求是Fragment要实现UpdateAble接口
            ((UpdateAble) fragment).update();
        }
    }

    public interface UpdateAble {
        public void update();
    }
}
