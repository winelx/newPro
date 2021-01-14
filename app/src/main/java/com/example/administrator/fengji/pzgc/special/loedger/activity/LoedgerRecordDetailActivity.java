package com.example.administrator.fengji.pzgc.special.loedger.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.special.loedger.adapter.LoedgerRecordDetailAdapter;
import com.example.administrator.fengji.pzgc.special.loedger.bean.LoedgerRecordDetailBean;
import com.example.administrator.fengji.pzgc.special.loedger.model.LoedgerRecordDetailModel;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 9:38
 * @说明 专项施工方案单项详情
 **/

public class LoedgerRecordDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private SmartRefreshLayout refreshlayout;
    private EmptyRecyclerView recyclerView;
    private LoedgerRecordDetailAdapter madapter;

    private LoedgerRecordDetailModel model;
    private Observer<List<LoedgerRecordDetailBean>> Observer;
    private String sysMsgNoticeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        Intent intent = getIntent();
        refreshlayout = findViewById(R.id.refreshlayout);
        //禁止下拉
        refreshlayout.setEnableRefresh(false);
        //禁止上拉
        refreshlayout.setEnableLoadmore(false);
        //仿ios越界
        refreshlayout.setEnableOverScrollBounce(true);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        title.setText("查看详情");
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        madapter = new LoedgerRecordDetailAdapter(R.layout.adapter_loedgerrecorddetail, new ArrayList<>());
        recyclerView.setAdapter(madapter);
        model = ViewModelProviders.of(this).get(LoedgerRecordDetailModel.class);
        Observer = new Observer<List<LoedgerRecordDetailBean>>() {
            @Override
            public void onChanged(@Nullable List<LoedgerRecordDetailBean> strings) {
                madapter.setNewData(strings);
            }
        };
        try {
            sysMsgNoticeId = intent.getStringExtra("id");
        } catch (Exception e) {
            sysMsgNoticeId = null;
        }
        model.getData(intent.getStringExtra("id"), sysMsgNoticeId).observe(this, Observer);
        model.setRequestError(new LoedgerRecordDetailModel.Modelinface() {
            @Override
            public void onerror() {
                ToastUtils.showShortToast("请求失败");
            }
        });
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
}
