package com.example.administrator.newsdf.pzgc.activity.device.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.MyExpandableListAdapter;
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
 * @description:特种设备全部消息
 */

public class DeviceAllFragment extends LazyloadFragment {
    private LinearLayout nullposion;
    private SmartRefreshLayout refreshLayout;
    private ExpandableListView expandableListView;
    private MyExpandableListAdapter mAdapter;
    private DeviceUtils deviceUtils;
    private Context mContext;
    private int groupPosition = 0;
    private View.OnClickListener ivGoToChildClickListener;
    @Override
    protected int setContentView() {
        return R.layout.fragment_allmessage;
    }

    @Override
    protected void init() {
        mContext=getActivity();
        //帮助类
        deviceUtils = new DeviceUtils();
        //空数据提示
        nullposion = rootView.findViewById(R.id.nullposion);
        //刷新加载
        refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
        //两层结构的listview
        expandableListView = rootView.findViewById(R.id.expandable);
        ivGoToChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取被点击图标所在的group的索引
                Map<String, Object> map = (Map<String, Object>) v.getTag();
                groupPosition = (int) map.get("groupPosition");
                //判断分组是否展开
                boolean isExpand = expandableListView.isGroupExpanded(groupPosition);
                if (isExpand) {
                    //收缩
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    //展开
                    expandableListView.expandGroup(groupPosition);
                }
            }
        };
    }

    @Override
    protected void lazyLoad() {
        deviceUtils.deviceall(new DeviceUtils.AllOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<String> list, Map<String, List<Home_item>> map) {
                mAdapter = new MyExpandableListAdapter(list, map, mContext,
                        ivGoToChildClickListener);
                expandableListView.setAdapter(mAdapter);
            }
        });
    }
}
