package com.example.administrator.newsdf.pzgc.Adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.example.administrator.newsdf.pzgc.callback.Onclicktener;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.adapter.multiitem.MultipleItemRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends MultipleItemRvAdapter<Object, BaseViewHolder> {
    private ArrayList<Object> list;
    public static final int TYPE_ONE = 01;
    public static final int TYPE_TWO = 02;
    public static final int TYPE_THREE = 03;

    public NoticeAdapter(@Nullable List<Object> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Object message) {
        //返回对应的viewType
        if (message instanceof NoticedBean) {
            return TYPE_ONE;
        } else if (message instanceof AgencyBean) {
            return TYPE_TWO;
        } else if (message instanceof CompleteBean) {
            return TYPE_THREE;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册相关的条目provider
        mProviderDelegate.registerProvider(new NoticedBeanItemProvider());
        mProviderDelegate.registerProvider(new AgencyItemProvider());
        mProviderDelegate.registerProvider(new CompleteItemProvider());
    }

}
