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
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.newsdf.pzgc.bean.Audio;
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
    private ArrayList<Audio> mData;
    private IconTextView checklistback;
    private SmartRefreshLayout smartrefreshlayout;
    private CheckUtils checkUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        category_list = view.findViewById(R.id.category_list);
        mData = new ArrayList<>();
        checkUtils = new CheckUtils();
        smartrefreshlayout = view.findViewById(R.id.smartrefreshlayout);
        smartrefreshlayout.setEnableRefresh(false);//是否启用下拉刷新功能
        smartrefreshlayout.setEnableLoadmore(false);//是否启用上拉加载功能
        smartrefreshlayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        adapter = new SettingAdapter<Audio>(mData, R.layout.check_standard_content) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, Audio obj) {
                holder.setText(R.id.check_stndard_content_font, obj.getName());
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
        checkUtils.CheckStandardApp(mData, adapter);
        return view;
    }


}
