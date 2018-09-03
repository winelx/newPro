package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTaskCategoryActivity;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;
import com.example.administrator.newsdf.pzgc.callback.CategoryCallback;
import com.example.administrator.newsdf.pzgc.callback.CategoryCallbackUtils;

import java.util.ArrayList;


/**
 * description:  检查项详情
 *
 * @author lx
 *         date: 2018/8/6 0006 上午 9:59
 *         update: 2018/8/6 0006
 *         version:
 */
public class CategoryContent extends Fragment implements CategoryCallback {
    private View view;
    private ListView category_list;
    private SettingAdapter adapter;
    private ArrayList<Tenanceview> mData;

    private CheckUtils checkUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        category_list = view.findViewById(R.id.category_list);
        TextView titleView =view.findViewById(R.id.titleView);
        titleView.setText("类别");
        mData = new ArrayList<>();
        checkUtils = new CheckUtils();
        CategoryCallbackUtils.setCallBack(this);

        adapter = new SettingAdapter<Tenanceview>(mData, R.layout.task_category_item) {
            @Override
            public void bindView(ViewHolder holder, Tenanceview obj) {
                holder.setText(R.id.category_content, obj.getName());
            }
        };
        category_list.setAdapter(adapter);
        category_list.setEmptyView(view.findViewById(R.id.nullposion));
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTaskCategoryActivity activity = (CheckTaskCategoryActivity) getActivity();
                activity.dismiss();
            }
        });
        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckTaskCategoryActivity activity = (CheckTaskCategoryActivity) getActivity();
                activity.result(mData.get(position).getName(), mData.get(position).getId());
            }
        });
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTaskCategoryActivity activity = (CheckTaskCategoryActivity) getActivity();
                activity.dismiss();
            }
        });

        return view;
    }

    @Override
    public void updata(String str, String str1) {
        mData.clear();
        CheckTaskCategoryActivity activity = (CheckTaskCategoryActivity) getActivity();
        checkUtils.taskTypeList(str, mData,activity.getType(), adapter);
    }
}
