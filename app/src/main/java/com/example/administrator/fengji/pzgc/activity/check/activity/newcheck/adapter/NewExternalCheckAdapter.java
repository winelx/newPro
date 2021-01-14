package com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.bean.ProcesshiscordBean;
import com.example.administrator.fengji.pzgc.utils.Utils;

import java.util.List;

public class NewExternalCheckAdapter extends BaseQuickAdapter<ProcesshiscordBean, BaseViewHolder> {
    public NewExternalCheckAdapter(int layoutResId, @Nullable List<ProcesshiscordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProcesshiscordBean item) {
        helper.setText(R.id.check_data, Utils.isNull(item.getDealDate()).substring(0, 16));
        helper.setText(R.id.dealPerson, Utils.isNull(item.getDealPerson()));
        helper.setText(R.id.dealContent, Utils.isNull(item.getDealContent()));
    }
}
