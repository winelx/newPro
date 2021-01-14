package com.example.administrator.fengji.pzgc.activity.changed.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.changed.ChangedNewActivity;
import com.example.administrator.fengji.pzgc.bean.ChagedNoticeDetailslsit;

import java.util.List;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/1/31 0031}
 * 描述：新增整改通知单的问题项
 * {@link ChangedNewActivity}
 */

public class ChangedNewAdapter extends BaseQuickAdapter<ChagedNoticeDetailslsit, BaseViewHolder> {
    public ChangedNewAdapter(int layoutResId, @Nullable List<ChagedNoticeDetailslsit> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChagedNoticeDetailslsit item) {
            helper.setText(R.id.content,item.getStandardDelName());
    }
}
