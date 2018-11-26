package com.example.zcjlmodule.ui.fragment.approval;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.ProcedurePageAdapter;
import com.example.zcjlmodule.bean.FlowListBean;
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
    private ArrayList<FlowListBean> list;

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
        adapter.setItemClickListener(new ProcedurePageAdapter.TypeItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtlis.getInstance().showShortToast("列表第" + position + "位" + ";集合第" + (position - 1) + "位");
            }
        });
    }

    @Override
    protected void lazyLoad() {

        adapter.setNewData(list);
    }
}
