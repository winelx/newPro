package com.example.administrator.newsdf.pzgc.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.bean.TodayDetailsBean;
import com.example.administrator.newsdf.pzgc.bean.TotalDetailsBean;
import com.example.baselibrary.adapter.multiitem.MultipleItemRvAdapter;
import com.github.mikephil.charting.data.PieData;

import java.util.List;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 首页任务统计模块三个界面的适配器
 * @see
 */
public class HomeTaskDetailsAdapter extends MultipleItemRvAdapter<Object, BaseViewHolder> {
    public static final int TYPE_ONE = 01;
    public static final int TYPE_TWO = 02;
    public static final int TYPE_THREE = 03;

    public HomeTaskDetailsAdapter(@Nullable List<Object> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Object object) {
        if (object instanceof TotalDetailsBean) {
            return TYPE_ONE;
        } else if (object instanceof TodayDetailsBean) {
            return TYPE_TWO;
        } else if (object instanceof LastmonthBean) {
            return TYPE_THREE;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        /*累计任务统计标段详情*/
        mProviderDelegate.registerProvider(new TotalDetailsAdapter());
        /*今日任务统计标段详情*/
        mProviderDelegate.registerProvider(new TodayDetailsAdapter());
        /*上月整改单统计标段详情*/
        mProviderDelegate.registerProvider(new LastmonthDetailsAdapter());

    }
}
