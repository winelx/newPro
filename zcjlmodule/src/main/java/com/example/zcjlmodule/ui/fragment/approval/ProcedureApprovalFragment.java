package com.example.zcjlmodule.ui.fragment.approval;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.ProcedurePageAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:申请单/拨款审批单(流程)
 */

public class ProcedureApprovalFragment extends LazyloadFragment {
    private EmptyRecyclerView emptyRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private ProcedurePageAdapter adapter;
    private View emptyView;
    private Context context;
    private ArrayList<String> list;

    @Override
    protected int setContentView() {
        return R.layout.fragment_page_zc;
    }

    @Override
    protected void init() {
        context = getActivity();
        list = new ArrayList<>();
        emptyView = rootView.findViewById(R.id.recycler_empty);
        refreshLayout = rootView.findViewById(R.id.page_refreshlayout);
        emptyRecyclerView = rootView.findViewById(R.id.fragment_messag_empty);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        emptyRecyclerView.setEmptyView(emptyView);
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        emptyRecyclerView.setAdapter(adapter = new ProcedurePageAdapter(list, context));
        emptyView.setVisibility(View.GONE);

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                //空白布局

                refreshLayout.finishRefresh();
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ArrayList<String> data = new ArrayList<>();
                data.add("测试数据");
                data.add("测试数据");
                list.addAll(data);
                refreshLayout.finishLoadmore();
                adapter.setNewData(data, 0);

            }
        });
        adapter.setItemClickListener(new ProcedurePageAdapter.TypeItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtlis.getInstance().showShortToast("列表第" + position + "位" + ";集合第" + (position - 1) + "位");
            }
        });
    }

    @Override
    protected void lazyLoad() {
        for (int i = 0; i < 10; i++) {
            list.add("测试数据");
        }
        adapter.setNewData(list);
    }
}
