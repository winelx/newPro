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
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTaskCategoryActivity;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;
import com.example.administrator.newsdf.pzgc.callback.CategoryCallbackUtils;

import java.util.ArrayList;


/**
 * description: 检查项列表
 *
 * @author lx
 *         date: 2018/8/6 0006 上午 9:59
 *         update: 2018/8/6 0006
 *         version:
 */
public class Categorylist extends Fragment {
    private View view;
    private ListView categoryList;
    private SettingAdapter adapter;
    private ArrayList<Tenanceview> mData;
    private CheckUtils checkUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        categoryList = view.findViewById(R.id.category_list);
        TextView titleView =view.findViewById(R.id.titleView);
        titleView.setText("类别");
        checkUtils = new CheckUtils();
        mData = new ArrayList<>();
        adapter = new SettingAdapter<Tenanceview>(mData, R.layout.task_category_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, Tenanceview obj) {
                holder.setText(R.id.category_content, obj.getName());
                holder.setVisibility(R.id.icontextview, 0);
            }
        };
        categoryList.setAdapter(adapter);
        categoryList.setEmptyView(view.findViewById(R.id.nullposion));
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = mData.get(position).getUnmber();
                int integer = Integer.parseInt(str);
                if (integer > 0) {
                    CheckTaskCategoryActivity activity = (CheckTaskCategoryActivity) getActivity();
                    activity.setItem();
                    CategoryCallbackUtils.CallBackMethod(mData.get(position).getId(),"");
                } else {
                    ToastUtils.showShortToastCenter("没有更多");
                }

            }
        });
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTaskCategoryActivity activity = (CheckTaskCategoryActivity) getActivity();
                activity.dismiss();
            }
        });

        checkUtils.taskTypeList("", mData, adapter);
        return view;
    }


}
