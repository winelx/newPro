package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;

import java.util.List;

public class NewExternalCheckGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public NewExternalCheckGridAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text_item,(helper.getLayoutPosition()+1)+"");
    }
}
