package com.example.administrator.yanghu.pzgc.special.loedger.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.special.loedger.adapter.LoedgerDetailAdapter;
import com.example.administrator.yanghu.pzgc.special.loedger.bean.DetailsOption;
import com.example.administrator.yanghu.pzgc.special.loedger.model.LoedgerDetailsModel;
import com.example.administrator.yanghu.pzgc.utils.EmptyUtils;
import com.example.administrator.yanghu.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author lx
 * @创建时间 2019/7/31 0031 15:31
 * @说明 专项整改台账详情
 * @see LoedgerDetailAdapter
 **/

@SuppressLint("Registered")
public class LoedgerDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView title, toolbar_title;
    private Button examine;
    private SmartRefreshLayout refreshlayout;
    private EmptyRecyclerView recyclerView;
    private LoedgerDetailAdapter mDetailAdapter;
    private EmptyUtils emptyUtils;
    private Utils utils;
    private Context mContext;
    private boolean type;
    private Observer<List<Object>> observer;
    private LoedgerDetailsModel detailsModel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loedgerdetails);
        mContext = this;
        emptyUtils = new EmptyUtils(this);
        utils = new Utils();
        intent = getIntent();
        type = intent.getBooleanExtra("type", false);
        findViewById(R.id.com_back).setOnClickListener(this);
        refreshlayout = findViewById(R.id.refreshlayout);
        //禁止下拉
        refreshlayout.setEnableRefresh(false);
        //禁止上拉
        refreshlayout.setEnableLoadmore(false);
        //仿ios越界
        refreshlayout.setEnableOverScrollBounce(true);
        title = findViewById(R.id.com_title);
        title.setText(intent.getStringExtra("title"));
        toolbar_title = findViewById(R.id.toolbar_title);
        //审核按钮
        examine = findViewById(R.id.examine);
        examine.setVisibility(View.GONE);
        examine.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailAdapter = new LoedgerDetailAdapter(new ArrayList<>());
        recyclerView.setAdapter(mDetailAdapter);
        mDetailAdapter.setEmptyView(emptyUtils.init());
        detailsModel = ViewModelProviders.of(this).get(LoedgerDetailsModel.class);
        observer = new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> objects) {
                if (objects.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
                mDetailAdapter.setNewData(objects);
            }
        };
        detailsModel.getCallback(new LoedgerDetailsModel.Permissioncallback() {
            @Override
            public void callback(String str, String msg) {
                if (!TextUtils.isEmpty(msg)) {
                    toolbar_title.setText(msg);
                    toolbar_title.setVisibility(View.VISIBLE);
                }
                if (type) {
                    if ("1".equals(str)) {
                        examine.setVisibility(View.VISIBLE);
                        utils.setMargins(refreshlayout, 0, 0, 0, Utils.dp2px(mContext, 45));
                    } else {
                        examine.setVisibility(View.GONE);
                    }
                }
            }
        });
        mDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DetailsOption bean = (DetailsOption) adapter.getData().get(position);
                Intent intent1 = new Intent(new Intent(mContext, LoedgerRecordDetailActivity.class));
                intent1.putExtra("id", bean.getId());
                startActivity(intent1);
            }
        });
        //回调
        LiveDataBus.get().with("details", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        request();
                        examine.setVisibility(View.GONE);
                    }
                });
        //根据入口控制提交按钮显示隐藏
        if (!type) {
            examine.setVisibility(View.GONE);
        }
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.examine:
                Intent intent1 = new Intent(mContext, LoedgerApprovalActivity.class);
                intent1.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    public void request() {
        detailsModel.getData(intent.getStringExtra("id"), intent.getStringExtra("taskId")).observe(this, observer);
    }
}
