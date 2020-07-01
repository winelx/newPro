package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.fragment;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class ExternalProjectType2Fragment extends LazyloadFragment {
    private ImageView list_add;
    private TextView title;
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recyclerView;
    private ProjectTypeAdapter typeAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activiy_check_external_list;
    }

    @Override
    protected void init() {
        list_add = (ImageView) findViewById(R.id.list_add);
        list_add.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.com_title);
        title.setText("选择工程类型");
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<CheckType> lsit = new ArrayList<>();
        lsit.add(new CheckType("绿化工程", ""));
        lsit.add(new CheckType("交安工程", ""));
        typeAdapter = new ProjectTypeAdapter(R.layout.taskphoto_item, lsit);
        recyclerView.setAdapter(typeAdapter);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus.get().with("project_type").setValue(0);
            }
        });
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckType checkType= (CheckType) adapter.getData().get(position);
                LiveDataBus.get().with("projecttype").setValue(checkType);
                getActivity().finish();
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    class ProjectTypeAdapter extends BaseQuickAdapter<CheckType, BaseViewHolder> {

        public ProjectTypeAdapter(int layoutResId, @Nullable List<CheckType> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CheckType item) {
            helper.setText(R.id.pop_task_item, item.getName());
        }
    }
}
