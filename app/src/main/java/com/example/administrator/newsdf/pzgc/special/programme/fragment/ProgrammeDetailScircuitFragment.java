package com.example.administrator.newsdf.pzgc.special.programme.fragment;


import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.FiletypeAdapter;
import com.example.administrator.newsdf.pzgc.special.programme.adapter.ProgrammedetailscircuitAdapter;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 15:23
 * @说明 施工方案详情:流程
 **/

public class ProgrammeDetailScircuitFragment extends LazyloadFragment {
    private ProgrammedetailscircuitAdapter mAdapter;
    private RecyclerView recyclerView;
    private EmptyUtils emptyUtils;
    private Map<String, ArrayList<ProDetails.RecordListBean>> map;

    @Override
    protected int setContentView() {
        return R.layout.fragment_programmedetail_scircuit;
    }

    @Override
    protected void init() {
        map = new HashMap<>();
        LiveDataBus.get().with("prodetails_scr", ProDetails.class)
                .observe(this, new Observer<ProDetails>() {
                    @Override
                    public void onChanged(@Nullable ProDetails bean) {
                        ArrayList<ProDetails.RecordListBean> list = (ArrayList<ProDetails.RecordListBean>) bean.getRecordList();
                        if (list != null) {
                            process(list, bean.getData().getStatus());
                        } else {
                            emptyUtils.noData("暂无记录");
                        }

                    }
                });
        emptyUtils = new EmptyUtils(getContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProgrammedetailscircuitAdapter(map, new ArrayList<>(), getContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {

    }

    public void process(ArrayList<ProDetails.RecordListBean> list,String status) {
        ArrayList<String> title = new ArrayList<>();
        title.add("头部");
        for (int i = 0; i < list.size(); i++) {
            String org = list.get(i).getOwnOrg();
            if ("0".equals(org)) {
                if (!title.contains("申报")) {
                    title.add("申报");
                    procedure(list, "申报", org);
                }

            } else if ("1".equals(org)) {
                if (!title.contains("申报单位审查意见")) {
                    title.add("申报单位审查意见");
                    procedure(list, "申报单位审查意见", org);
                }

            } else if ("2".equals(org)) {
                if (!title.contains("分公司审查意见")) {
                    title.add("分公司审查意见");
                    procedure(list, "分公司审查意见", org);
                }
            } else if ("3".equals(org)) {
                if (!title.contains("集团公司总工办时效审查")) {
                    title.add("集团公司总工办时效审查");
                    procedure(list, "集团公司总工办时效审查", org);
                }
            } else if ("4".equals(org)) {
                if (!title.contains("集团公司总工办及相关部门审查")) {
                    title.add("集团公司总工办及相关部门审查");
                    procedure(list, "集团公司总工办及相关部门审查", org);
                }
            } else if ("5".equals(org)) {
                if (!title.contains("集团公司总工程师审批")) {
                    title.add("集团公司总工程师审批");
                    procedure(list, "集团公司总工程师审批", org);
                }
            }

        }
        title.add("尾部");
        mAdapter.setNewData(map, title,status);
    }

    public void procedure(ArrayList<ProDetails.RecordListBean> list, String str, String num) {
        ArrayList<ProDetails.RecordListBean> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String org = list.get(i).getOwnOrg();
            if (org.equals(num)) {
                data.add(list.get(i));
            }
        }
        map.put(str, data);
    }
}
