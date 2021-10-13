package com.example.administrator.yanghu.pzgc.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.pzgc.bean.AgencyBean;
import com.example.baselibrary.adapter.multiitem.MultipleItemRvAdapter;

import java.util.List;

public class NoticeAdapter extends MultipleItemRvAdapter<Object, BaseViewHolder> {
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
