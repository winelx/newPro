package com.example.administrator.newsdf.pzgc.special.loedger.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.adapter.LoedgerDetailAdapter;
import com.example.administrator.newsdf.pzgc.special.loedger.model.LoedgerDetailsModel;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.view.EmptyRecyclerView;

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
    private EmptyRecyclerView recyclerView;
    private LoedgerDetailAdapter mDetailAdapter;
    private EmptyUtils emptyUtils;
    private List<Object> list;

    private Observer<List<Object>> observer;
    private LoedgerDetailsModel detailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        list = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailAdapter = new LoedgerDetailAdapter(list);
        recyclerView.setAdapter(mDetailAdapter);
        mDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        detailsModel = ViewModelProviders.of(this).get(LoedgerDetailsModel.class);
        observer = new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> objects) {
                ToastUtils.showShortToast(objects.size()+"");
                mDetailAdapter.setNewData(objects);
            }
        };
        detailsModel.getData().observe(this, observer);
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
