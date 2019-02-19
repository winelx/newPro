package com.example.administrator.newsdf.pzgc.activity.changed;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ImportChageditemAdapter;
import com.example.administrator.newsdf.pzgc.bean.ChagedImportitem;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
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
 * 创建日期：{2019/2/1 0001}
 * 描述：导入整改项
 * {@link }
 */
public class ChagedImportitemActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ImportChageditemAdapter adapter;
    private RecyclerView recyclerList;
    private ArrayList<ChagedImportitem> list;
    private EmptyUtils emptyUtils;
    private Context mContext;
    private ChagedUtils chagedUtils;
    private SmartRefreshLayout refreshlayout;
    private String orgId;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_importchageditem);
        mContext = this;
        final Intent intent = getIntent();
        orgId = intent.getStringExtra("orgid");
        list = new ArrayList<>();
        chagedUtils = new ChagedUtils();
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.com_title);
        title.setText("导入问题项");
        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImportChageditemAdapter(R.layout.adapter_item_improtchageditem, list);
        //设置空白数据显示界面
        adapter.setEmptyView(emptyUtils.init());
        recyclerList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //监督检查Id传递过去
                Intent intent1 = new Intent(mContext, CheckitemActivity.class);
                intent1.putExtra("checkManageId", list.get(position).getId());
                intent1.putExtra("title", list.get(position).getTitlle());
                intent1.putExtra("content", list.get(position).getContent());
                startActivityForResult(intent1, 1);
            }
        });
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
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

    private void request() {
        chagedUtils.choosemanagedatalist(orgId, page, new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                if (page == 1) {
                    list.clear();
                }
                ArrayList<ChagedImportitem> data = (ArrayList<ChagedImportitem>) map.get("list");
                list.addAll(data);
                adapter.setNewData(list);
                if (list.size() == 0) {
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
            }

            @Override
            public void onerror(String str) {
                ToastUtils.showsnackbar(title, str);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果导入成功，回调
        if (requestCode == 1 && resultCode == 1) {
            finish();
        }
    }
}
