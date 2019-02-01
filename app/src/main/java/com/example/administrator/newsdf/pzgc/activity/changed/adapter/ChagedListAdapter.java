package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedListActivity;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单列表适配器
 * {@link  ChagedListActivity}
 */
public class ChagedListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChagedListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
