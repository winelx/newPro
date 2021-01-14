package com.example.administrator.fengji.pzgc.activity.notice.fragment;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.fengji.R;

import com.example.administrator.fengji.pzgc.activity.notice.Model.NoticeModel;
import com.example.administrator.fengji.pzgc.bean.Proclamation;
import com.example.administrator.fengji.pzgc.utils.EmptyUtils;
import com.example.baselibrary.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ author： lx
 * @ 创建时间： 2019/5/29 0029 9:56
 * @ 说明： 通知公告
 **/
public class MessageNoticeFragment extends BaseActivity implements View.OnClickListener {
    private TextView comTitle;
    private RecyclerView recycler;
    private SmartRefreshLayout reshlayout;
    private EmptyUtils emptyUtils;
    private NoticeModel noticeModel;
    private Observer<List<Proclamation>> observer;
    private Adapter adapter;
    private List<Proclamation> list;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_messagenotice);
        comTitle = findViewById(R.id.com_title);
        reshlayout = findViewById(R.id.reshlayout);
        recycler = findViewById(R.id.recycler);
        findViewById(R.id.com_back).setOnClickListener(this);
        list = new ArrayList<>();
        comTitle.setText("通知公告");
        recycler.setLayoutManager(new LinearLayoutManager(this));
        emptyUtils = new EmptyUtils(this);
        adapter = new Adapter(R.layout.messagenotice_item, new ArrayList<>());
        recycler.setAdapter(adapter);
        //设置空白布局
        adapter.setEmptyView(emptyUtils.init());
        //初始化viewmodel
        noticeModel = ViewModelProviders.of(this).get(NoticeModel.class);
        //根据Ui
        observer = new Observer<List<Proclamation>>() {
            @Override
            public void onChanged(@Nullable List<Proclamation> data) {
                list.clear();
                list.addAll(data);
                if (data != null) {
                    adapter.setNewData(list);
                }
                if (data.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
            }
        };
        //适配器点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, NoticeDetailsFragment.class);
                intent.putExtra("ids", list.get(position).getId());
                startActivity(intent);
            }
        });
        //下拉刷新
        reshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                reqeuse();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        //上拉加载
        reshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                reqeuse();
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        //网络请求
        reqeuse();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }

    /*网络请求*/
    private void reqeuse() {
        Map<String, String> map = new HashMap<>();
        map.put("nowPage", page + "");
        noticeModel.getmData(map).observe(this, observer);
    }

    /*适配器*/
    private class Adapter extends BaseQuickAdapter<Proclamation, BaseViewHolder> {
        public Adapter(int layoutResId, @Nullable List<Proclamation> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, Proclamation item) {
            helper.setText(R.id.content, item.getTitle());
            //组织
            helper.setText(R.id.orgnanme, item.getOrgName());
            //创建人
            helper.setText(R.id.username, item.getCreateName());
            //时间
            helper.setText(R.id.timedata, item.getPublishDate().substring(0, 10));
        }
    }
}
