package com.example.administrator.fengji.pzgc.activity.check.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.adapter.SettingAdapter;
import com.example.administrator.fengji.pzgc.activity.check.CheckUtils;
import com.example.administrator.fengji.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.fengji.pzgc.bean.Audio;
import com.example.administrator.fengji.pzgc.callback.CategoryCallbackUtils;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

/**
 * description:
 *
 * @author lx
 *         date: 2018/8/20 0020 上午 8:41
 *         update: 2018/8/20 0020
 *         version:
 */
public class CheckstandardContent extends Fragment {
    private View view;
    private ListView category_list;
    private SettingAdapter adapter;
    private ArrayList<Audio> mData;
    private IconTextView checklistback;

    private CheckUtils checkUtils;
    private TextView titleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        category_list = view.findViewById(R.id.category_list);
        mData = new ArrayList<>();
        checkUtils = new CheckUtils();
        titleView = view.findViewById(R.id.titleView);
        titleView.setText("检查标准");
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
                CategoryCallbackUtils.CallBackMethod(mData.get(position).getContent(), mData.get(position).getName());
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                String str=mData.get(position).getName();
                String content=mData.get(position).getContent();
                activity.setItem();
                activity.getdata(str,content);

            }
        });
        view.findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.dismiss();
            }
        });
        checkUtils.getdatecon(mData,adapter);
        return view;
    }


}
