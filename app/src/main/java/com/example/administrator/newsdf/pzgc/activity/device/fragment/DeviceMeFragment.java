package com.example.administrator.newsdf.pzgc.activity.device.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/27 0027.
 * @description:特种设备我的消息
 */

public class DeviceMeFragment extends LazyloadFragment {
    private LinearLayout nullposion;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView homeList;
    private DeviceUtils deviceUtils;

    @Override
    protected int setContentView() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void init() {
        //帮助类
        deviceUtils = new DeviceUtils();
        //空数据提示
        nullposion = rootView.findViewById(R.id.nullposion);
        //刷新加载
        refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
        //recyclerview
        homeList = rootView.findViewById(R.id.home_list);
    }

    @Override
    protected void lazyLoad() {
        //网络请求
        deviceUtils.deviceme(new DeviceUtils.MeOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<String> list) {

            }
        });
    }
}
