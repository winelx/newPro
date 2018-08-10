package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.joanzapata.iconify.widget.IconTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class CheckstandardContent extends Fragment {
    private View view;
    private ListView category_list;
    private SettingAdapter adapter;
    private ArrayList<String> mData;
    private IconTextView checklistback;
    private SmartRefreshLayout smartrefreshlayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        category_list = view.findViewById(R.id.category_list);
        mData = new ArrayList<>();
        mData.add("基本要求" + "”" + "10不准" + "”");
        mData.add("特种设备" + "”" + "5不准" + "”");
        mData.add("路基工程" + "”" + "1不准" + "”");
        mData.add("桥梁工程" + "”" + "12不准" + "”");
        mData.add("隧道工程" + "”" + "7不准" + "”");
        smartrefreshlayout = view.findViewById(R.id.smartrefreshlayout);
        smartrefreshlayout.setEnableRefresh(false);//是否启用下拉刷新功能
        smartrefreshlayout.setEnableLoadmore(false);//是否启用上拉加载功能
        smartrefreshlayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        adapter = new SettingAdapter<String>(mData, R.layout.check_standard_content) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, String obj) {
                holder.setText(R.id.check_stndard_content_font, obj);
            }
        };
        category_list.setAdapter(adapter);
        category_list.setEmptyView(view.findViewById(R.id.nullposion));
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.dismiss();
            }
        });
        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.setItem();
            }
        });
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.dismiss();
            }
        });


        return view;
    }
}
