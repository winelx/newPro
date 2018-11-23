package com.example.zcjlmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:已办界面recyclerview 的适配器
 * @Fragment:HavedonePageFragmentZc
 */

public class HavedonePageFragmentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HavedonePageFragmentAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.havedone_title, item);
    }

}
