package com.example.zcjlmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:未办界面recyclerview 的适配器
 * @Fragment:AgencyPageFragmentZc
 */
public class AgencyPageFragmentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AgencyPageFragmentAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }
}
