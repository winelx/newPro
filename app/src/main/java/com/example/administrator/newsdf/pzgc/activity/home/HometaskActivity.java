package com.example.administrator.newsdf.pzgc.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TodayBean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.HometasksAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.bean.TotalBean;
import com.example.administrator.newsdf.pzgc.fragment.HomeFragment;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * @data:2019/3/6 0006
 * @Notes: 消息界面任务数量统计模块展示界面
 * @see HomeFragment
 */
@SuppressLint("Registered")
public class HometaskActivity extends BaseActivity implements View.OnClickListener {
    private EmptyRecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private TextView title;
    private Context mContext;
    private HometasksAdapter adapter;
    private EmptyUtils emptyUtils;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometask);
        mContext = this;
        list = new ArrayList<>();
        Intent intent = getIntent();
        emptyUtils = new EmptyUtils(mContext);
        title = findViewById(R.id.com_title);
        title.setText(intent.getStringExtra("title"));
        recyclerView = findViewById(R.id.recycler);
        //设置展示style
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置适配器
        recyclerView.setAdapter(adapter = new HometasksAdapter(list));
        //设置空白提示界面
        adapter.setEmptyView(emptyUtils.init());
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        findViewById(R.id.com_back).setOnClickListener(this);
        if (Enums.ADDUPTask.equals(title.getText().toString())) {
            cumulativeRequest();
        } else if (Enums.TODAYTASK.equals(title.getText().toString())) {
            todayRequest();
        } else if (Enums.LASTMONTHTASK.equals(title.getText().toString())) {
            lastmontRequest();
        }
        adapter.setNewData(list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (title.getText().toString().equals(Enums.ADDUPTask)) {
                    //累计完成任务
                    TotalBean totalBean = (TotalBean) list.get(position);
                    Intent intent1 = new Intent(mContext, HomeTaskDetailsActivity.class);
                    intent1.putExtra("type", Enums.ADDUPTask);
                    intent1.putExtra("id", totalBean.getfOrgId());
                    intent1.putExtra("title", totalBean.getfOrgName());
                    startActivity(intent1);
                } else if (title.getText().toString().equals(Enums.TODAYTASK)) {
                    //今日完成任务
                    TotalBean todayBean= (TotalBean) list.get(position);
                    Intent intent = new Intent(mContext, HomeTaskDetailsActivity.class);
                    intent.putExtra("type", Enums.TODAYTASK);
                    intent.putExtra("id", todayBean.getfOrgId());
                    intent.putExtra("title",todayBean.getfOrgName());
                    startActivity(intent);
                } else if (title.getText().toString().equals(Enums.LASTMONTHTASK)) {
                    //上月整改单统计
                    LastmonthBean bean = (LastmonthBean) list.get(position);
                    Intent intent2 = new Intent(mContext, HomeTaskDetailsActivity.class);
                    intent2.putExtra("type", Enums.LASTMONTHTASK);
                    intent2.putExtra("title", bean.getName());
                    intent2.putExtra("id", bean.getId());
                    startActivity(intent2);
                }
            }
        });
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
    /**上月整改单统计*/
    private void lastmontRequest() {
        HomeFragmentUtils.getNoticeCountData(null, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey("lastmonth")) {
                    list.addAll((ArrayList<LastmonthBean>) map.get("lastmonth"));
                    adapter.setNewData(list);
                }
                if (list.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });
    }
    /**累计完成任务*/
    private void cumulativeRequest() {
        HomeFragmentUtils.cumulativeRequest(new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey("total")) {
                    list.addAll((ArrayList<TotalBean>) map.get("total"));
                    adapter.setNewData(list);
                }
                if (list.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });

    }
    /**今日完成*/
    private void todayRequest() {
        HomeFragmentUtils.todayRequest(new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey("today")) {
                    list.addAll((ArrayList<TotalBean>) map.get("today"));
                    adapter.setNewData(list);
                }
                if (list.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });
    }
}

