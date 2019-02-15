package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedReplyNewActivity;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：新增整改列表
 * {@link  ChagedReplyNewActivity}
 */
public class ChangedReplyNewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChangedReplyNewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
