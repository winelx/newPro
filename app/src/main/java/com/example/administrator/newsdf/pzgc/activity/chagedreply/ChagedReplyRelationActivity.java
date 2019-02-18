package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChagedReplyRelationAdapter;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.RelationList;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.baselibrary.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：关联整改通知单
 * {@link }
 */
public class ChagedReplyRelationActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ChagedReplyRelationAdapter adapter;
    private RecyclerView recyclerList;
    private SmartRefreshLayout refreshlayout;
    private ArrayList<RelationList> list;
    private EmptyUtils emptyUtils;
    private Context mContext;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_importchageditem);
        mContext = this;
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.com_title);
        title.setText("关联整改通知单");
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChagedReplyRelationAdapter(R.layout.adapter_item_improtreply, list);
        adapter.setEmptyView(emptyUtils.init());
        recyclerList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id", "12");
                intent.putExtra("str", "12");
                setResult(1, intent);
                fileList();
            }
        });
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                request();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        /* 上拉加载*/
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                request();
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        request();
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

    public void request() {
        ChagedreplyUtils.getNoticeFormList(SPUtils.getString(mContext, "orgId", ""), page, new ChagedreplyUtils.MapCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                if (page == 1) {
                    list.clear();
                }
                list = (ArrayList<RelationList>) map.get("Relation");
                adapter.setNewData(list);
                if (list.size() == 0) {
                    emptyUtils.noData("暂无数据,下拉刷新");
                }
            }

            @Override
            public void onerror(String str) {
                if (page != 1) {
                    page--;
                }
                Snackbar.make(title, str, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
