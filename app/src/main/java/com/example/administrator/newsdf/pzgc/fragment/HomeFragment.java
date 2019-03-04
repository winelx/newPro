package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.HomemessageAdapter;
import com.example.administrator.newsdf.pzgc.bean.Homenotice;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.baselibrary.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：消息推送通知页
 * {@link }
 */

public class HomeFragment extends LazyloadFragment {
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView recycler;
    private Context mContext;
    private HomemessageAdapter adapter;
    private ArrayList<Tenanceview> list;
    private ArrayList<Homenotice> noticeslist;

    @Override
    protected int setContentView() {
        return R.layout.fragment_homemessage;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        noticeslist = new ArrayList<>();
        noticeslist.add(new Homenotice(R.mipmap.fr_work_miss, "", "", "", "", ""));
        noticeslist.add(new Homenotice(R.mipmap.fr_work_rectification, "", "", "", "", ""));
        noticeslist.add(new Homenotice(R.mipmap.fr_work_miss, "", "", "", "", ""));
        list = new ArrayList<>();
        list.add(new Tenanceview("#5096F8", "累计完成任务数", "100,000"));
        list.add(new Tenanceview("#f88c37", "今日完成任务数", "500"));
        list.add(new Tenanceview("#28c26A", "上月检查单数", "10"));
        list.add(new Tenanceview("#ffaa09", "上月通知单数", "100"));

        //列表控件
        recycler = rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        //刷新加载控件
        refreshLayout = rootView.findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        /* 下拉刷新*/
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        /*请求数据*/
    }


}
