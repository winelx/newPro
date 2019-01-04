package com.example.zcjlmodule.ui.fragment.approval;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.AccumulativePageAdapter;
import com.example.zcjlmodule.bean.PeriodListBean;
import com.example.zcjlmodule.ui.activity.apply.ApplyHeadquartersZcActivity;
import com.example.zcjlmodule.ui.activity.approval.ApprovalZcActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;


/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:拨款审批单(累计)
 * @Activity ApprovalZcActivity
 */

public class AccumulativeApprovalFragment extends LazyloadFragment {
    private EmptyRecyclerView emptyRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private AccumulativePageAdapter adapter;
    private View emptyView;
    private Context context;
    private ArrayList<PeriodListBean> list;
    private TextView pageApplyExamine, pageApplyPrice;

    @Override
    protected int setContentView() {
        return R.layout.fragment_current_page;
    }

    @Override
    protected void init() {
        //拿到承载fragment的activity的对象；
        context = ApprovalZcActivity.getInstance();
        //实例化activity
        ApprovalZcActivity applyActivityZc = (ApprovalZcActivity) context;
        list = new ArrayList<>();
        list=applyActivityZc.getperiodList();
        emptyView = rootView.findViewById(R.id.recycler_empty);
        refreshLayout = rootView.findViewById(R.id.page_refreshlayout);
        emptyRecyclerView = rootView.findViewById(R.id.fragment_messag_empty);
        //审批
        pageApplyExamine = rootView.findViewById(R.id.page_apply_examine);
        pageApplyExamine.setVisibility(View.GONE);
        pageApplyPrice = rootView.findViewById(R.id.page_apply_price);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //设置空数据布局
        emptyRecyclerView.setEmptyView(emptyView);
        //设置数据展示样式
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //设置分割线
        emptyRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //设置适配器
        emptyRecyclerView.setAdapter(adapter = new AccumulativePageAdapter(R.layout.adapter_accumulative, list));
        //初始化时没有请求网络，列表无数据，会展示空数据提示，所以隐藏
        emptyView.setVisibility(View.GONE);
        //点击事件处理
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, ApplyHeadquartersZcActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 界面第一次展示时处理数据，
     */
    @Override
    protected void lazyLoad() {
        adapter.setNewData(list);
    }
}
