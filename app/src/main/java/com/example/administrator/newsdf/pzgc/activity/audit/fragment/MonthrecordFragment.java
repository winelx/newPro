package com.example.administrator.newsdf.pzgc.activity.audit.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DailyrecordAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class MonthrecordFragment extends Fragment {
    private View rootView;
    private Context mContext;
    private TextView datatime;
    private DailyrecordAdapter mAdapter;
    private ListView daily_list;
    private ArrayList<String> list = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_month, null);
            daily_list = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
        }
        for (int i = 0; i < 100; i++) {
            list.add("ass" + i);
        }
        datatime.setText("选择月份");
        mContext = getActivity();
        mAdapter = new DailyrecordAdapter(mContext, list);
        daily_list.setAdapter(mAdapter);
        return rootView;
    }
}
