package com.example.administrator.fengji.pzgc.activity.chagedreply.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.chagedreply.ChagedReplyNewActivity;
import com.example.administrator.fengji.pzgc.activity.chagedreply.utils.bean.Chagereplydetails;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：列表
 * {@link  ChagedReplyNewActivity}
 */
public class ChangedReplyNewAdapter extends BaseQuickAdapter<Chagereplydetails, BaseViewHolder> {
    public ChangedReplyNewAdapter(int layoutResId, @Nullable List<Chagereplydetails> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Chagereplydetails item) {
        helper.setText(R.id.content, item.getStandardDelName());
        int isReply = item.getIsReply();
        if (isReply == 1) {
            helper.setBackgroundRes(R.id.status, R.mipmap.circular_ensure_false);
        } else {
            helper.setBackgroundRes(R.id.status, R.mipmap.circular_ensure_true);

        }
    }
}
