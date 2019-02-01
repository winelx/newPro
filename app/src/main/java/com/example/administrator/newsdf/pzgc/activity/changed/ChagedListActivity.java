package com.example.administrator.newsdf.pzgc.activity.changed;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedListAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.EmptyUtils;
import com.example.baselibrary.PullDownMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单列表
 * {@link }
 */
public class ChagedListActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recyclerList;
    private TextView title;
    private ImageView toolbar_image;
    private ChagedListAdapter adapter;
    private ArrayList<String> list;
    private EmptyUtils emptyUtils;
    private Context mContext;
    private PullDownMenu pullDownMenu;

    String[] strings = {"全部", "未处理", "已处理"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(mContext);
        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        title = (TextView) findViewById(R.id.com_title);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        toolbar_image = (ImageView) findViewById(R.id.com_img);
        toolbar_image.setImageResource(R.mipmap.meun);
        toolbar_image.setVisibility(View.VISIBLE);
        findViewById(R.id.com_back).setOnClickListener(this);
        //设置列表参数
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        recyclerList.setAdapter(adapter = new ChagedListAdapter(R.layout.adapter_item_chaged_list, list));
        adapter.setEmptyView(emptyUtils.init());
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                for (int i = 0; i < 10; i++) {
                    list.add("ceshi");
                }
                adapter.setNewData(list);
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        /* 上拉加载*/
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        emptyUtils.noData("暂无数据，下拉刷新");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.toolbar_menu:
                pullDownMenu = new PullDownMenu();
                pullDownMenu.showPopMeun((Activity) mContext, toolbar_image, strings);
                pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
                    @Override
                    public void onclick(int position, String string) {
                        switch (string) {
                            case "全部":
                                ToastUtils.showShortToast("全部");
                                startActivity(new Intent(mContext,ChagedNoticeDetailsActivity.class));
                                break;
                            case "未处理":
                                ToastUtils.showShortToast("未处理");
                                break;
                            case "已处理":
                                ToastUtils.showShortToast("已处理");
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
