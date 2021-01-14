package com.example.administrator.fengji.pzgc.activity.device.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.adapter.CheckMessageMineAdapter;
import com.example.administrator.fengji.pzgc.activity.device.DeviceMessageListActivity;
import com.example.administrator.fengji.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.fengji.pzgc.bean.Home_item;
import com.example.administrator.fengji.pzgc.callback.CallBack;
import com.example.administrator.fengji.pzgc.callback.DeviceMeCallbackUtils;
import com.example.administrator.fengji.pzgc.utils.LazyloadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/27 0027.
 * @description: 我的消息
 */

public class DeviceMeFragment extends LazyloadFragment implements CallBack {
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
        DeviceMeCallbackUtils.setCallBack(this);
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
                Intent intent = new Intent(mContext, DeviceMessageListActivity.class);
                 intent.putExtra("orgId", list.get(position).getId());
                 intent.putExtra("orgName", list.get(position).getOrgname());
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

    //更新数据
    @Override
    public void deleteTop() {
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
