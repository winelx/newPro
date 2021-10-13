package com.example.administrator.yanghu.pzgc.activity.device.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.adapter.DeviceExpandableAdapter;
import com.example.administrator.yanghu.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.yanghu.pzgc.bean.Home_item;
import com.example.administrator.yanghu.pzgc.utils.LazyloadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/11/27 0027.
 * @注释: 特种设备全部消息
 */

public class DeviceAllFragment extends LazyloadFragment {
    private LinearLayout nullposion, probar;
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
        probar = rootView.findViewById(R.id.probar);
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
                probar.setVisibility(View.GONE);
            }
        });
    }
}
