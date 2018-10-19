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
import com.example.zcmodule.R;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * description: 征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 2:31
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面 workFragment
 */
public class StandardDismantlingZcActivity extends BaseActivity {
    private DismantAdapter mAdapter;
    private List<String> list;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_dismantling_zc);
        mContext=this;
        list = new ArrayList<>();
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        for (int i = 0; i < 10; i++) {
            list.add("0");
        }
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("征拆标准");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dismantling_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new DismantAdapter(R.layout.adapter_standard_dismantling_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               startActivity(new Intent(mContext,ExamineDismantlingActivity.class));
            }
        });
        //子项的点击事件处理
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.see_standard_dismantiling:
                        startActivity(new Intent(mContext,UnknitstandardActivity.class));
                        break;
                    default:
                        break;
                }

            }
        });

    }

    class DismantAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public DismantAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.addOnClickListener(R.id.see_standard_dismantiling);

        }
    }
}
