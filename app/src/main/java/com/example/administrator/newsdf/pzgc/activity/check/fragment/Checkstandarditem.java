package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.content.Context;
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
import com.example.administrator.newsdf.pzgc.adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.newsdf.pzgc.bean.standarBean;
import com.example.administrator.newsdf.pzgc.callback.CategoryCallback;
import com.example.administrator.newsdf.pzgc.callback.CategoryCallbackUtils;

import java.util.ArrayList;


/**
 * description: 检查标准列表
 *
 * @author lx
 *         date: 2018/8/9 0009 上午 10:51
 *         update: 2018/8/9 0009
 *         version:
 */
public class Checkstandarditem extends Fragment implements CategoryCallback {
    private ArrayList<standarBean> mData;
    private TextView titleView;
    private CheckUtils checkUtils;
    private SettingAdapter adapter;
    private Context mContext;
    private boolean lean = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        ListView categoryList = view.findViewById(R.id.category_list);
        mData = new ArrayList<>();
        mContext = getActivity();
        CategoryCallbackUtils.setCallBack(this);
        checkUtils = new CheckUtils();
        titleView = view.findViewById(R.id.titleView);
        adapter = new SettingAdapter<standarBean>(mData, R.layout.task_category_item) {
            @Override
            public void bindView(ViewHolder holder, standarBean obj) {
                if (lean) {
                    int score = Integer.parseInt(obj.getStandardDelScore()) / 2;
                    String standardDelScore = "";
                    String StandardDelName = obj.getStandardDelName();
                    int num = StandardDelName.length();
                    for (int i = 0; i < score; i++) {
                        standardDelScore = standardDelScore + "★";
                    }
                    StandardDelName = StandardDelName + standardDelScore;
                    holder.setText(mContext, R.id.category_content, StandardDelName, num - 1, R.color.red);
                } else {
                    holder.setText(R.id.category_content, obj.getStandardDelName());
                }
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
                String DelName = mData.get(position).getStandardDelName();
                DelName = DelName.substring(2, DelName.length());
                String code = mData.get(position).getStandardDelCode();
                CheckstandardListActivity activity = (CheckstandardListActivity) getActivity();
                activity.result(DelName, mData.get(position).getStandardDel(), mData.get(position).getStandardDelScore(), code);
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

    public boolean isJO(int num) {
        int a = num % 2;
        if (a == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updata(String str, String str1) {
        titleView.setText(str1);
        mData.clear();
        //网络请求
        checkUtils.CheckStandardApp(str, new CheckUtils.Onclick() {
            @Override
            public void onSuccess(ArrayList<standarBean> List) {
                int count = 0;
                for (standarBean item : List) {
                    try {
                        int score = Integer.parseInt(item.getStandardDelScore());
                        boolean status = isJO(score);
                        if (!status) {
                            count++;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                 if (count == 0) {
                    lean = true;
                } else {
                    lean = false;
                }
                mData.addAll(List);
                adapter.getData(mData);
            }
        });
    }

}
