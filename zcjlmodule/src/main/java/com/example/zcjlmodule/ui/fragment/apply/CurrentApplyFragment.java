package com.example.zcjlmodule.ui.fragment.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.CurrentPageFragmentAdapter;
import com.example.zcjlmodule.bean.CurrentApplyBean;
import com.example.zcjlmodule.ui.activity.apply.ApplyActivityZc;
import com.example.zcjlmodule.ui.activity.apply.ApplyHeadquartersZcActivity;
import com.example.zcjlmodule.utils.fragment.CurrentApplyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.math.BigDecimal;
import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.utils.BaseDialogUtils;
import measure.jjxx.com.baselibrary.utils.BigDecimalUtils;
import measure.jjxx.com.baselibrary.utils.TextUtils;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:申请单(本期)
 */

public class CurrentApplyFragment extends LazyloadFragment implements View.OnClickListener {
    private EmptyRecyclerView emptyRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private CurrentPageFragmentAdapter adapter;
    private View emptyView;
    private Context context;
    private BigDecimal price;
    private ArrayList<CurrentApplyBean> list;
    private TextView pageApplyExamine, pageApplyPrice;

    @Override
    protected int setContentView() {
        return R.layout.fragment_current_page;
    }

    @Override
    protected void init() {
        context = ApplyActivityZc.getInstance();
        ApplyActivityZc applyActivityZc = (ApplyActivityZc) context;
        String str = applyActivityZc.getstatus();
        list = new ArrayList<>();
        list.addAll(applyActivityZc.getcountlist());
        try {
            for (int i = 0; i < list.size(); i++) {
                String Amount = list.get(i).getToThisPeriodAmount();
                price = BigDecimalUtils.safeAdd(price, new BigDecimal(Amount));
            }
        }catch (Exception e){
        }

        emptyView = rootView.findViewById(R.id.recycler_empty);
        refreshLayout = rootView.findViewById(R.id.page_refreshlayout);
        emptyRecyclerView = rootView.findViewById(R.id.fragment_messag_empty);
        pageApplyPrice = rootView.findViewById(R.id.page_apply_price);
        //审批
        pageApplyExamine = rootView.findViewById(R.id.page_apply_examine);
        pageApplyExamine.setOnClickListener(this);
        if (str != null) {
            pageApplyExamine.setVisibility(View.GONE);
        }
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //调加空白布局
        emptyRecyclerView.setEmptyView(emptyView);
        //设置数据展示样式
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //设置分割线
        emptyRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //设置适配器
        emptyRecyclerView.setAdapter(adapter = new CurrentPageFragmentAdapter(R.layout.adapter_accumulative, list));
        //隐藏空白布局
        emptyView.setVisibility(View.GONE);
        try {
            pageApplyPrice.setText(TextUtils.setText(context, "总计:"+price.toString(), 3));
        }catch (Exception e){
            pageApplyPrice.setText(TextUtils.setText(context, "总计:", 3));
        }
        //item点击事件处理
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, ApplyHeadquartersZcActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 当界面第一次可见时进行网络请求
     */
    @Override
    protected void lazyLoad() {

    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_apply_examine:
                //弹出审批框，并返回数据
                BaseDialogUtils.checkandcontent(context, new BaseDialogUtils.dialogonclick() {
                    @Override
                    public void onsuccess(String status, String content) {

                    }
                });
                break;
            default:
                break;

        }
    }


}
