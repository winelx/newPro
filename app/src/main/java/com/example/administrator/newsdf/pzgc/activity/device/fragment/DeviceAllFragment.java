package com.example.administrator.newsdf.pzgc.activity.device.fragment;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.DeviceExpandableAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/11/27 0027.
 * @description: 特种设备全部消息
 */

public class DeviceAllFragment extends LazyloadFragment {
    private LinearLayout nullposion;
    private SmartRefreshLayout refreshLayout;
    private ExpandableListView expandableListView;
    private DeviceExpandableAdapter mAdapter;
    private DeviceUtils deviceUtils;
    private Context mContext;

    @Override
    protected int setContentView() {
        return R.layout.fragment_allmessage;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        //帮助类
        deviceUtils = new DeviceUtils();
        //空数据提示
        nullposion = rootView.findViewById(R.id.nullposion);
        //刷新加载
        refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //两层结构的listview
        expandableListView = rootView.findViewById(R.id.expandable);

    }

    @Override
    protected void lazyLoad() {
        deviceUtils.deviceall(new DeviceUtils.AllOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<String> list, Map<String, List<Home_item>> map) {
                mAdapter = new DeviceExpandableAdapter(list, map, mContext);
                expandableListView.setAdapter(mAdapter);
            }
        });
    }
}
