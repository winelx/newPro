package com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.utils;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class DrawableUtils {

    /**
     * 说明：拦截侧滑界面展开还能对列表进行滑动的处理
     * 创建时间： 2020/5/29 0029 16:19
     *
     * @author winelx
     */
    public static void setDrawLayout(DrawerLayout drawerLayout) {
        //拦截
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}
