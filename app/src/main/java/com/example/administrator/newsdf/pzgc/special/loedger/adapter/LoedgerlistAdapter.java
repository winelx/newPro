package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Author lx
 * @创建时间  2019/7/31 0031 13:32
 * @说明 
 **/

public class LoedgerlistAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LoedgerlistAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
