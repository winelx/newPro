package com.example.baselibrary.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description: 懒加载fragment 集成baseFragment
 */

public abstract class LazyloadFragment extends Fragment {
    protected View rootView;
    public boolean isInitView = false;
    private boolean isVisible = false;
    public Context mContext;
    private Bundle savedInstanceState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setContentView(), container, false);
            mContext = getActivity();
            isInitView = true;
            this.savedInstanceState = savedInstanceState;
            init();
            isCanLoadData();
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
        }
    }

    private void isCanLoadData() {
        //所以条件是view初始化完成并且对用户可见
        if (isInitView && isVisible) {
            lazyLoad();
            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 加载页面布局文件
     *
     * @return
     */
    protected abstract int setContentView();


    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract void init();

    /**
     * 加载要显示的数据
     */
    protected abstract void lazyLoad();

    public View findViewById(int v) {
        return rootView.findViewById(v);
    }

    public Bundle getBuild() {
        return savedInstanceState;
    }

}
