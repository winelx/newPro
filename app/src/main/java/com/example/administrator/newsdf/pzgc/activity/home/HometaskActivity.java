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

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.HometaskAdapter;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.bean.TodayBean;
import com.example.administrator.newsdf.pzgc.bean.TotalBean;
import com.example.administrator.newsdf.pzgc.callback.Onclicktener;
import com.example.administrator.newsdf.pzgc.fragment.HomeFragment;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

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
    private HometaskAdapter adapter;
    private EmptyUtils emptyUtils;

    private ArrayList<Object> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometask);
        mContext = this;
        list = new ArrayList<>();
        Intent intent = getIntent();
        final String str = intent.getStringExtra("title");
        emptyUtils = new EmptyUtils(mContext);
        title = findViewById(R.id.com_title);
        title.setText(str);
        recyclerView = findViewById(R.id.recycler);
        //设置空白提示界面
        recyclerView.setEmptyView(emptyUtils.init());
        //设置展示style
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置适配器
        recyclerView.setAdapter(adapter = new HometaskAdapter(mContext, list));
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        findViewById(R.id.com_back).setOnClickListener(this);
        if (Enums.ADDUPTask.equals(str)) {
            for (int i = 0; i < 10; i++) {
                list.add(new TotalBean("累计完成任务"));
            }
        } else if (Enums.TODAYTASK.equals(str)) {
            for (int i = 0; i < 10; i++) {
                list.add(new TodayBean("今日完成任务"));
            }
        } else if (Enums.LASTMONTHTASK.equals(str)) {
            for (int i = 0; i < 10; i++) {
                list.add(new LastmonthBean("上月整改统计"));
            }
        }
        adapter.setNewData(list);
        adapter.setOnclicktener(new Onclicktener() {
            @Override
            public void onClick(String string, int position) {
                switch (string) {
                    case Enums.ADDUPTask:
                        //累计完成任务
                        Intent intent1 = new Intent(mContext, HomeTaskDetailsActivity.class);
                        intent1.putExtra("type", Enums.ADDUPTask);
                        startActivity(intent1);
                        break;
                    case Enums.TODAYTASK:
                        //今日完成任务
                        Intent intent = new Intent(mContext, HomeTaskDetailsActivity.class);
                        intent.putExtra("type", Enums.TODAYTASK);
                        startActivity(intent);
                        break;
                    case Enums.LASTMONTHTASK:
                        //上月整改单统计
                        Intent intent2 = new Intent(mContext, HomeTaskDetailsActivity.class);
                        intent2.putExtra("type", Enums.LASTMONTHTASK);
                        startActivity(intent2);
                        break;
                    default:
                        break;
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
}

