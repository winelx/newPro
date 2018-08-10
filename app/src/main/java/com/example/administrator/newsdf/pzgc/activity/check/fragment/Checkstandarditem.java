package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/9 0009.
 */
/**
 * description: 检查标准列表
 * @author lx
 * date: 2018/8/9 0009 上午 10:51
 * update: 2018/8/9 0009
 * version:
*/
public class Checkstandarditem extends Fragment {
    private ArrayList<String> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        ListView categoryList = view.findViewById(R.id.category_list);
        mData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mData.add("整改数据" + i);
        }
        SmartRefreshLayout smartrefreshlayout = view.findViewById(R.id.smartrefreshlayout);
        smartrefreshlayout.setEnableRefresh(false);//是否启用下拉刷新功能
        smartrefreshlayout.setEnableLoadmore(false);//是否启用上拉加载功能
        smartrefreshlayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果)
        SettingAdapter adapter = new SettingAdapter<String>(mData, R.layout.task_category_item) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.category_content, obj);
            }
        };
        categoryList.setAdapter(adapter);
        categoryList.setEmptyView(view.findViewById(R.id.nullposion));
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.dismiss();
            }
        });
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.result(mData.get(position));
            }
        });
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.dismiss();
            }
        });
        /**
         *   下拉刷新
         */
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        smartrefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });

        return view;
    }
}
