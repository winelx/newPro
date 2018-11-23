package com.example.zcjlmodule.ui.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.HavedonePageFragmentAdapter;
import com.example.zcjlmodule.utils.fragment.ApprovalFragmentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:messageFragment界面的已办数据展示界面
 */

public class HavedonePageFragmentZc extends LazyloadFragment {
    private EmptyRecyclerView emptyRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private HavedonePageFragmentAdapter adapter;
    private View emptyView;
    private Context context;
    private ArrayList<String> list;
    private ApprovalFragmentUtils fragmentUtils;

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
        fragmentUtils = new ApprovalFragmentUtils();
        emptyView = rootView.findViewById(R.id.recycler_empty);
        refreshLayout = rootView.findViewById(R.id.page_refreshlayout);
        emptyRecyclerView = rootView.findViewById(R.id.fragment_messag_empty);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //空白布局
        emptyRecyclerView.setEmptyView(emptyView);
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.custom_divider));
        emptyRecyclerView.addItemDecoration(divider);
        emptyRecyclerView.setAdapter(adapter = new HavedonePageFragmentAdapter(R.layout.adapter_havedone, list));
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        emptyView.setVisibility(View.GONE);
    }

    /**
     * 加载要显示的数据
     */
    @Override
    protected void lazyLoad() {
        //网络请求
//        fragmentUtils.Havedonerequest(new AgencyPageFragmentUtils.HavedoneOnClickListener() {
//            @Override
//            public void onsuccess(ArrayList<String> list) {
//                adapter.setNewData(list);
//            }
//        });
        for (int i = 0; i < 20; i++) {
            list.add("添加数据" + i);
        }
        adapter.setNewData(list);
    }

}
