package com.example.administrator.newsdf.pzgc.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.HomeTaskDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.LastmonthDetailsBean;
import com.example.administrator.newsdf.pzgc.bean.TodayDetailsBean;
import com.example.administrator.newsdf.pzgc.bean.TotalDetailsBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * @author lx
 * @data :2019/3/7 0007
 * @描述 : 标段任务详情
 * @see HometaskActivity
 */
public class HomeTaskDetailsActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView recyclerView;
    private EmptyUtils emptyUtils;
    private Context mContext;
    private HomeTaskDetailsAdapter adapter;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometask);
        mContext = this;
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        refreshLayout = findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, VERTICAL));
        adapter = new HomeTaskDetailsAdapter(list);
        adapter.setEmptyView(emptyUtils.init());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("点击");
            }
        });
        Intent intent = getIntent();
        final String type = intent.getStringExtra("type");
        ToastUtils.showShortToastCenter(type);
        if (type.equals(Enums.ADDUPTask)) {
            //累计完成任务
            for (int i = 0; i < 10; i++) {
                list.add(new TotalDetailsBean("测试数据" + i));
            }
            adapter.setNewData(list);
        } else if (type.equals(Enums.TODAYTASK)) {
            //今日完成任务i
            for (int i = 0; i < 10; i++) {
                list.add(new TodayDetailsBean("测试数据" + i));
            }
            adapter.setNewData(list);
        } else if (type.equals(Enums.LASTMONTHTASK)) {
            //上月整改统计
            for (int i = 0; i < 10; i++) {
                list.add(new LastmonthDetailsBean("测试数据" + i));
            }
            adapter.setNewData(list);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
