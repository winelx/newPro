package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedReplyImportActivity;
import com.example.administrator.newsdf.pzgc.bean.Checkitem;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：导入问题项
 * {@link ChagedReplyImportActivity}
 */
public class ChagedReplyImportAdapter extends BaseQuickAdapter<Checkitem, BaseViewHolder> {
    public ChagedReplyImportAdapter(int layoutResId, @Nullable List<Checkitem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Checkitem item) {
        boolean status = item.isLeam();
        if (status) {
            helper.setBackgroundRes(R.id.chaged_reply_image, R.mipmap.circular_ensure_true);
        } else {
            helper.setBackgroundRes(R.id.chaged_reply_image, R.mipmap.circular_ensure_false);
        }
    }
}
