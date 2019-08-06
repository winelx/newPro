package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;

import java.util.List;

/**
 * @Author lx
 * @创建时间  2019/8/6 0006 9:12
 * @说明 台账审批适配器
 **/

public class ProgrammeAuditorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProgrammeAuditorAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.agree_provision_chk);
    }
}
