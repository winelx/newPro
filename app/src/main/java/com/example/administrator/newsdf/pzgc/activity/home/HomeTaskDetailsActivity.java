package com.example.administrator.newsdf.pzgc.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.HomeTaskDetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

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
    private TextView title;
    private EmptyUtils emptyUtils;
    private Context mContext;
    private HomeTaskDetailsAdapter adapter;
    private ArrayList<Object> list;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometask);
        mContext = this;
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        refreshLayout = findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
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
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        final String type = intent.getStringExtra("type");
        title.setText(intent.getStringExtra("title"));
        if (type.equals(Enums.ADDUPTask)) {
            //累计完成任务
            GrandTaskRequest();
        } else if (type.equals(Enums.TODAYTASK)) {
            //今日完成任务i
            todayRequest();
        } else if (type.equals(Enums.LASTMONTHTASK)) {
            LasetRequest();
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

    private void LasetRequest() {
        HomeFragmentUtils.getNoticeCountData(id, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey("lastmonth")) {
                    list.addAll((ArrayList<LastmonthBean>) map.get("lastmonth"));
                    adapter.setNewData(list);
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });
    }

    /*累计任务*/
    private void GrandTaskRequest() {
        HomeFragmentUtils.grandTaskFinish(id, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey("list")) {
                    list.addAll((ArrayList<LastmonthBean>) map.get("list"));
                    adapter.setNewData(list);
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });
    }

    /*今日任务*/
    private void todayRequest() {
        HomeFragmentUtils.todayDetailsRequest(id, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey("list")) {
                    list.addAll((ArrayList<LastmonthBean>) map.get("list"));
                    adapter.setNewData(list);
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });
    }

}
