package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Author lx
 * @创建时间  2019/8/1 0001 11:14
 * @说明
 **/

public class LoedgerRecordDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LoedgerRecordDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
