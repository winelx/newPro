package com.example.administrator.newsdf.pzgc.special.loedger.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.adapter.LoedgerDetailAdapter;
import com.example.administrator.newsdf.pzgc.special.loedger.model.LoedgerDetailsModel;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.bean;
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
    private TextView title;
    private Button examine;
    private SmartRefreshLayout refreshlayout;
    private EmptyRecyclerView recyclerView;
    private LoedgerDetailAdapter mDetailAdapter;
    private EmptyUtils emptyUtils;
    private Utils utils;
    private List<Object> list;
    private Context mContext;

    private Observer<List<Object>> observer;
    private LoedgerDetailsModel detailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loedgerdetails);
        Intent intent = getIntent();
        boolean type = intent.getBooleanExtra("type", false);
        mContext = this;
        refreshlayout = findViewById(R.id.refreshlayout);
        //禁止下拉
        refreshlayout.setEnableRefresh(false);
        //禁止上拉
        refreshlayout.setEnableLoadmore(false);
        //仿ios越界
        refreshlayout.setEnableOverScrollBounce(true);
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(this);
        utils = new Utils();
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        //审核按钮
        examine = findViewById(R.id.examine);
        //根据入口控制提交按钮显示隐藏
        if (type) {
            examine.setVisibility(View.GONE);
        } else {
            examine.setVisibility(View.VISIBLE);
        }
        examine.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailAdapter = new LoedgerDetailAdapter(list);
        recyclerView.setAdapter(mDetailAdapter);
        mDetailAdapter.setEmptyView(emptyUtils.init());
        mDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        detailsModel = ViewModelProviders.of(this).get(LoedgerDetailsModel.class);
        observer = new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> objects) {
                ToastUtils.showShortToast(objects.size() + "");
                mDetailAdapter.setNewData(objects);

            }
        };
        detailsModel.getData(intent.getStringExtra("id"), intent.getStringExtra("taskId")).observe(this, observer);
//        utils.setMargins(refreshlayout, 0, Utils.dp2px(this, 75), 0, Utils.dp2px(this, 45));
        mDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, LoedgerRecordDetailActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.examine:
                startActivity(new Intent(mContext, LoedgerApprovalActivity.class));
                break;
            default:
                break;
        }
    }
}
