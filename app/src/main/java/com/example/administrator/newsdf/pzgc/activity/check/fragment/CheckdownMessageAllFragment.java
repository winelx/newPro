package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckNewAddActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description: 全部检查通知
 *
 * @author lx
 *         date: 2018/8/8 0008 上午 10:09
 *         update: 2018/8/8 0008
 *         version:
 */
public class CheckdownMessageAllFragment extends Fragment {
    private ArrayList<String> list;
    private Map<String, List<Home_item>> map;
    private Context mContext;
    private ImageView checkNewadd;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.checkdown_all, container, false);
        mContext = getActivity();
        list = new ArrayList<>();
        map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            list.add("测试数据" + i);
        }
        ArrayList<Home_item> home = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            home.add(new Home_item("测试数据" + i, "2018-06-27 15:22:00", "测试数据" + i, "测试数据" + i, "测试数据" + i, "0", "0", "测试数据" + i, "测试数据" + i, false));
        }
        for (int i = 0; i < 20; i++) {
            map.put("测试数据" + i, home);
        }
        checkNewadd =view.findViewById(R.id.check_newadd);
        ExpandableListView expandable = view.findViewById(R.id.expandable);
        SmartRefreshLayout refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableLoadmore(false);
        CheckListAdapter mAdapter = new CheckListAdapter(list, map, mContext);
        expandable.setAdapter(mAdapter);
        refreshLayout.finishRefresh(true);
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        checkNewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CheckNewAddActivity.class));
            }
        });
        return view;
    }

}
