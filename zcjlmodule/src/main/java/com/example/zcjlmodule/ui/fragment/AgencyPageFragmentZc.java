package com.example.zcjlmodule.ui.fragment;

import android.content.Context;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.AgencyPageFragmentAdapter;
import com.example.zcjlmodule.ui.activity.apply.ApplyActivityZc;
import com.example.zcjlmodule.ui.activity.apply.DetailedlistActivity;
import com.example.zcjlmodule.utils.fragment.ApplyFragmentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:fragment代办展示数据界面
 */

public class AgencyPageFragmentZc extends LazyloadFragment {
    private EmptyRecyclerView emptyRecyclerView;
    private AgencyPageFragmentAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private View emptyView;
    private Context context;
    private ArrayList<String> list;
    private ApplyFragmentUtils fragmentUtils;

    /**
     * 加载页面布局文件
     *
     * @return
     */
    @Override
    public int setContentView() {
        return R.layout.fragment_page_zc;
    }

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    @Override
    protected void init() {
        context = getActivity();
        list = new ArrayList<>();
        fragmentUtils = new ApplyFragmentUtils();
        refreshLayout = rootView.findViewById(R.id.page_refreshlayout);
        //空白数据界面
        emptyView = rootView.findViewById(R.id.recycler_empty);
        //recyclerview
        emptyRecyclerView = rootView.findViewById(R.id.fragment_messag_empty);
        //数据展示样式
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //设置空白界面
        emptyRecyclerView.setEmptyView(emptyView);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.custom_divider));
        emptyRecyclerView.addItemDecoration(divider);
        //设置适配器
        emptyRecyclerView.setAdapter(adapter = new AgencyPageFragmentAdapter(R.layout.adapter_agency_zc, list));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, DetailedlistActivity.class);
                intent.putExtra("status",true);
                startActivity(intent);
            }
        });
        emptyView.setVisibility(View.GONE);
    }

    /**
     * 加载要显示的数据
     */
    @Override
    protected void lazyLoad() {
        /**
         * 网络请求
         */
//        fragmentUtils.Agencyrequest(new AgencyPageFragmentUtils.OnClickListener() {
//            @Override
//            public void onsuccess(ArrayList<String> data) {
//                adapter.setNewData(list);
//            }
//        });
        for (int i = 0; i < 2; i++) {
            list.add("测试数据" + i);
        }
        adapter.setNewData(list);
    }


}
