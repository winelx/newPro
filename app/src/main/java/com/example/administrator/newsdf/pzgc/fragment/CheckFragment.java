package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckFragmentAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.Checkbean;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckdownMessageActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckmanagementActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;

import java.util.ArrayList;

/**
 * description: 首页的检查模块
 *
 * @author lx
 *         date: 2018/8/2 0002 上午 11:14
 *         update: 2018/8/2 0002
 *         version:
 */
public class CheckFragment extends Fragment {
    private View rootView;
    private RecyclerView check_recycler;
    private Context mContext;
    private CheckFragmentAdapter adapter;
    private ArrayList<Checkbean> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_check, null);
            mContext = MainActivity.getInstance();
            check_recycler = rootView.findViewById(R.id.check_recycler);
        }
        list = new ArrayList<>();
        list.add(new Checkbean("检查标准", R.mipmap.check_standard));
        list.add(new Checkbean("检查管理", R.mipmap.check_management));
        list.add(new Checkbean("通知管理", R.mipmap.check_notice));
       list.add(new Checkbean("统计报表", R.mipmap.check_statistical));
        check_recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        adapter = new CheckFragmentAdapter(mContext, list);
        check_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new CheckFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, CheckstandardListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, CheckmanagementActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, CheckdownMessageActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, CheckReportActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
        return rootView;
    }
}
