package com.example.administrator.newsdf.pzgc.special.programme.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.adapter.ProgrammedetailscircuitAdapter;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 15:23
 * @说明 施工方案详情:流程
 **/

public class ProgrammeDetailScircuitFragment extends LazyloadFragment {
    private ProgrammedetailscircuitAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_programmedetail_scircuit;
    }

    @Override
    protected void init() {
        List<Object> lsit = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lsit.add("fdsa" + i);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProgrammedetailscircuitAdapter(R.layout.adapter_programme_scircuit, lsit);
        //添加Header对应的View
        View headerView = getLayoutInflater().inflate(R.layout.adapter_programme_scircuit_head, (ViewGroup) recyclerView.getParent(), false);
        //添加Header对应的View
        View footerView = getLayoutInflater().inflate(R.layout.adapter_programme_scircuit_footer, (ViewGroup) recyclerView.getParent(), false);
        //调用BaseQuickAdapter
        mAdapter.addHeaderView(headerView);
        mAdapter.addFooterView(footerView);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {

    }
}
