package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedReplyImportActivity;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ImprotItem;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：导入问题项
 * {@link ChagedReplyImportActivity}
 */
public class ChagedReplyImportAdapter extends BaseQuickAdapter<ImprotItem, BaseViewHolder> {
    public ChagedReplyImportAdapter(int layoutResId, @Nullable List<ImprotItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImprotItem item) {
        boolean status = item.isNewX();
        if (status) {
            helper.setBackgroundRes(R.id.chaged_reply_image, R.mipmap.circular_ensure_true);
        } else {
            helper.setBackgroundRes(R.id.chaged_reply_image, R.mipmap.circular_ensure_false);
        }
        helper.setText(R.id.chagedposition, "整改部位：" + item.getRectificationPartName());
        helper.setText(R.id.chagedstandard, "违反标准：" + item.getStandardDelName());
        helper.setText(R.id.chagedproblem, "存在问题：" + item.getRectificationReason());
        helper.setText(R.id.chageddata, "整改期限：" + item.getRectificationDate().substring(0, 10));
    }
}
