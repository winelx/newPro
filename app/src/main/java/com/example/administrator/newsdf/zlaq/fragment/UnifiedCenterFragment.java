package com.example.administrator.newsdf.zlaq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.zlaq.BaseFragment;
import com.example.administrator.newsdf.zlaq.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 统一登录平台首页应用界面
 *
 * @author lx
 *         date: 2018/7/2 0002 上午 10:01
 *         update: 2018/7/2 0002
 *         version:
 */

public class UnifiedCenterFragment extends BaseFragment {
    private View rootView;
    private Banner mBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_un_message, null);
            mBanner = rootView.findViewById(R.id.banner);

        }
        List<String> list = new ArrayList<>();
        list.add("http://img5.duitang.com/uploads/item/201301/14/20130114180854_UhfZx.jpeg");
        list.add("http://img5.duitang.com/uploads/item/201301/14/20130114180854_UhfZx.jpeg");
        list.add("http://img5.duitang.com/uploads/item/201301/14/20130114180854_UhfZx.jpeg");

        mBanner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }
}
