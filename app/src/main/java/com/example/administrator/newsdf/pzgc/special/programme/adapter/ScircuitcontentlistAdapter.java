package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;

import java.util.List;

public class ScircuitcontentlistAdapter extends BaseQuickAdapter<ProDetails.RecordListBean, BaseViewHolder> {
    public ScircuitcontentlistAdapter(int layoutResId, @Nullable List<ProDetails.RecordListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProDetails.RecordListBean item) {

    }
}
