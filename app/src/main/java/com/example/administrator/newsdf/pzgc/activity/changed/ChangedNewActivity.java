package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChangedNewAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/1/30 0030}
 * 描述：修改后的新增整改通知单
 * {@link }
 */
public class ChangedNewActivity extends BaseActivity implements View.OnClickListener {
    private TextView chaged_number;
    private RecyclerView recycler;
    private List<String> list;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_new);
        mContext = this;
        list = new ArrayList<>();
        list.add("测试数据测试数据测试数据");
        list.add("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
        list.add("测试数据测试数据测试数据");
        //返回
        findViewById(R.id.com_back).setOnClickListener(this);
        //下发
        findViewById(R.id.chaged_release_problem).setOnClickListener(this);
        //导入问题项
        findViewById(R.id.chaged_import_problem).setOnClickListener(this);
        //添加问题项
        findViewById(R.id.chaged_add_problem).setOnClickListener(this);
        //整改负责人
        findViewById(R.id.chaged_head_lin).setOnClickListener(this);
        //下发人
        findViewById(R.id.chaged_release_lin).setOnClickListener(this);
        //整改组织
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
        //问题项
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        ChangedNewAdapter adapter = new ChangedNewAdapter(R.layout.adapter_item_chaged_new, list);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.chaged_release_problem:
                //下发
                ToastUtils.showShortToast("下发");
                startActivity(new Intent(mContext, ChagedListActivity.class));
                break;
            case R.id.chaged_import_problem:
                ToastUtils.showShortToast("导入问题项");
                startActivity(new Intent(mContext,ImportChageditemActivity.class));
                break;
            case R.id.chaged_add_problem:
                startActivity(new Intent(mContext, ChagedProblemitemActivity.class));
                break;
            case R.id.chaged_head_lin:
                ToastUtils.showShortToast("整改负责人");
                break;
            case R.id.chaged_release_lin:
                ToastUtils.showShortToast("下发人");
                break;
            case R.id.chaged_organize_lin:
                ToastUtils.showShortToast("整改组织");
                break;
            default:
                break;
        }
    }
}
