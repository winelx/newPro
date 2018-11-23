package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.utils.activity.MeasureUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * description: 申请期数
 *
 * @author lx
 *         date: 2018/10/18 0018 下午 3:25
 *         update: 2018/10/18 0018
 *         跳转界面 NewAddOriginalZcActivity
 *         与AttachProjectZcActivity ChoiceBidsZcActivity  ChoiceHeadquartersZcActivity
 *         ApplyDateZcActivity 共用布局
 */
public class ApplyDateZcActivity extends BaseActivity implements View.OnClickListener {

    private AttachProjectAdapter mAdapter;
    private ArrayList<AttachProjectBean> list;
    private Context mContext;
    private ProgressBar layout_emptyView_bar;
    private TextView layout_emptyView_text;
    private LinearLayout layout_emptyView;
    private MeasureUtils utils;
    private SmartRefreshLayout refreshLayout;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_project_zc);
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        mContext = this;
        utils = new MeasureUtils();
        list = new ArrayList<>();
        //提示布局
        layout_emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待进度
        layout_emptyView_bar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //空白提示
        layout_emptyView_text = (TextView) findViewById(R.id.layout_emptyView_text);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.original_refreshlayout);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("选择期数");
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
                setResult(106, intent);
                finish(); //结束当前的activity的生命周期
            }
        });
        httprequest();
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                httprequest();
            }
        });
    }

    private void httprequest() {
        //网络请求
        utils.ascriptionsbqs(orgId, new MeasureUtils.OnClickListener() {
            @Override
            public void onsuccess(List<AttachProjectBean> data) {
                refreshLayout.finishRefresh();
                list.clear();
                list.addAll(data);
                mAdapter.setNewData(list);
                if (list.size() <= 0) {
                    layout_emptyView_text.setText("暂无数据");
                } else {
                    layout_emptyView.setVisibility(View.GONE);
                }
                layout_emptyView_bar.setVisibility(View.GONE);
            }

            @Override
            public void onerror() {
                refreshLayout.finishRefresh();
                layout_emptyView_bar.setVisibility(View.GONE);
                layout_emptyView_text.setText("请求失败");
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

    //适配器
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
