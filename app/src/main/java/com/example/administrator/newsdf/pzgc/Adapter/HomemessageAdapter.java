package com.example.administrator.newsdf.pzgc.Adapter;


import android.graphics.Color;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：MainActivity
 * {@link com.example.administrator.newsdf.pzgc.fragment.HomeFragment}
 */
public class HomemessageAdapter extends BaseQuickAdapter<Tenanceview, BaseViewHolder> {
    public HomemessageAdapter(int layoutResId, @Nullable List<Tenanceview> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tenanceview item) {
        helper.setText(R.id.number, item.getUnmber());
        helper.setText(R.id.name, item.getName());
        helper.setBackgroundColor(R.id.content, Color.parseColor(item.getId()));
    }
}
