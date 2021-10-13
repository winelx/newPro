package com.example.administrator.yanghu.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.bean.MoretasklistBean;

import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/8/6 0006 9:12
 * @说明 台账审批适配器
 **/

public class ProgrammeAuditorAdapter extends BaseQuickAdapter<MoretasklistBean, BaseViewHolder> {
    public ProgrammeAuditorAdapter(int layoutResId, @Nullable List<MoretasklistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoretasklistBean item) {
        helper.addOnClickListener(R.id.agree_provision_chk);
        helper.setText(R.id.username, item.getPartContent());
        helper.setText(R.id.occupation, item.getUploadTime());
        boolean lean = item.isLean();
        if (lean) {
            helper.setBackgroundRes(R.id.agree_provision_chk, R.mipmap.checkbox_blue);
        } else {
            helper.setBackgroundRes(R.id.agree_provision_chk, R.mipmap.checkbox_gray);
        }
    }
}
