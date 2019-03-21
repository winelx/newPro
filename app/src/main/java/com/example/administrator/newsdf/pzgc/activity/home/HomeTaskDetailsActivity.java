package com.example.administrator.newsdf.pzgc.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedListAllActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.TodayDetailsBean;
import com.example.administrator.newsdf.pzgc.bean.TotalDetailsBean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.HomeTaskDetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.bean.bean;
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
    public static final String LASTMONTH = "lastmonth";
    public static final String LIST = "list";

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
        //空数据提示
        adapter.setEmptyView(emptyUtils.init());
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        //id
        id = intent.getStringExtra("id");
        //类型
        final String type = intent.getStringExtra("type");
        //标题
        title.setText(intent.getStringExtra("title"));
        if (type.equals(Enums.ADDUPTask)) {
            //累计完成任务
            grandtaskrequest();
        } else if (type.equals(Enums.TODAYTASK)) {
            //今日完成任务i
            todayRequest();
        } else if (type.equals(Enums.LASTMONTHTASK)) {
            //上月整改统计
            lasetrequest();
        }
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type.equals(Enums.LASTMONTHTASK)) {
                    LastmonthBean bean = (LastmonthBean) list.get(position);
                    //上月整改统计
                    Intent notice = new Intent(mContext, ChagedListAllActivity.class);
                    notice.putExtra("orgid", bean.getId());
                    notice.putExtra("orgName", bean.getName());
                    notice.putExtra("modelType", true);
                    startActivity(notice);
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

    /*上月整改统计*/
    private void lasetrequest() {
        HomeFragmentUtils.getNoticeCountData(id, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey(LASTMONTH)) {
                    list.addAll((ArrayList<LastmonthBean>) map.get(LASTMONTH));
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
    private void grandtaskrequest() {
        HomeFragmentUtils.grandTaskFinish(id, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                if (map.containsKey(LIST)) {
                    list.addAll((ArrayList<TotalDetailsBean>) map.get(LIST));
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
                if (map.containsKey(LIST)) {
                    list.addAll((ArrayList<TodayDetailsBean>) map.get(LIST));
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
