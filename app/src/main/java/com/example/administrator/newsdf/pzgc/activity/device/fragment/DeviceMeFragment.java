package com.example.administrator.newsdf.pzgc.activity.device.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckMessageMineAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.ChecknoticeMessagelistActivity;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceMessageListActivity;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
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
    private Context mContext;
    private ArrayList<Home_item> list;
    private CheckMessageMineAdapter mAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        list = new ArrayList<>();
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
        //recyclerview
        homeList = rootView.findViewById(R.id.home_list);
        homeList.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分割线
        homeList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        homeList.setAdapter(mAdapter = new CheckMessageMineAdapter(mContext, list));
        mAdapter.setOnItemClickListener(new CheckMessageMineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showLongToast(position + "");
                Intent intent = new Intent(mContext, DeviceMessageListActivity.class);
//                intent.putExtra("status", false);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        //网络请求
        deviceUtils.deviceme(new DeviceUtils.MeOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<Home_item> data) {
                list.addAll(data);
                mAdapter.getData(data);
                if (list.size() > 0) {
                    nullposion.setVisibility(View.GONE);
                }
            }
        });
    }
}
