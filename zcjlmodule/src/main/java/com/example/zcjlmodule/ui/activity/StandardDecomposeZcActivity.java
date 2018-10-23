package com.example.zcjlmodule.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * description: 选择标准分解
 *
 * @author lx
 *         date: 2018/10/18 0018 下午 3:48
 *         update: 2018/10/18 0018
 *         version:
 *         跳转界面： NewAddOriginalZcActivity
 */
public class StandardDecomposeZcActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycler;
    private StandardDecomposeAdapter mAdapter;
    private Context mContext;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_decompose_zc);
        list = new ArrayList<>();
        mContext = this;
        //地区查询
        findViewById(R.id.standard_dec_region).setOnClickListener(this);
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //征拆类别查询
        findViewById(R.id.standard_dec_type).setOnClickListener(this);
        //单价查询
        findViewById(R.id.standard_dec_Price).setOnClickListener(this);
        for (int i = 0; i < 10; i++) {
            list.add("" + 1);
        }

        TextView titlke = (TextView) findViewById(R.id.toolbar_icon_title);
        titlke.setText("选择标准分解");
        recycler = (RecyclerView) findViewById(R.id.standard_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter = new StandardDecomposeAdapter(R.layout.adapter_decompose_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();

        } else if (i == R.id.standard_dec_region) {//地区查询

        } else if (i == R.id.standard_dec_type) {
            startActivity(new Intent(mContext, DismantlingtypequeryZcActivity.class));
            //类型查询

        } else if (i == R.id.standard_dec_Price) {//单价查询

        } else {
        }
    }

    public class StandardDecomposeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public StandardDecomposeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
