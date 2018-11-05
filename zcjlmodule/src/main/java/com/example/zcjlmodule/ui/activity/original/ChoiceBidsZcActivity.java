package com.example.zcjlmodule.ui.activity.original;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * description: 选择所属标段
 *
 * @author lx
 *         date: 2018/10/18 0018 下午 3:25
 *         update: 2018/10/18 0018
 *         跳转界面 NewAddOriginalZcActivity
 *         与AttachProjectZcActivity ChoiceBidsZcActivity  ChoiceHeadquartersZcActivity共用布局
 */
public class ChoiceBidsZcActivity extends BaseActivity implements View.OnClickListener {
    private AttachProjectAdapter mAdapter;
    private ArrayList<AttachProjectBean> list;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_project_zc);
        mContext = this;
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new AttachProjectBean("1" + i,"测试数据" + i));
        }
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("选择所属标段");
        RecyclerView recycler = (RecyclerView) findViewById(R.id.attachproject_recycler);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_divider));
        recycler.addItemDecoration(divider);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter = new AttachProjectAdapter(R.layout.adapter_choiceproject_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                // 获取用户计算后的结果
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("id", list.get(position).getId());
                setResult(103, intent);
                finish(); //结束当前的activity的生命周期
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();

        } else {
        }
    }

    private class AttachProjectAdapter extends BaseQuickAdapter<AttachProjectBean, BaseViewHolder> {
        public AttachProjectAdapter(@LayoutRes int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AttachProjectBean item) {
            helper.setText(R.id.attachproject_text, item.getName());
        }
    }

}
