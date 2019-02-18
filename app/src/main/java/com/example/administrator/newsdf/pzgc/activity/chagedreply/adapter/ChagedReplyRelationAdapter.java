package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedReplyRelationActivity;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.RelationList;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：关联整改通知单
 * {@link  ChagedReplyRelationActivity}
 */
public class ChagedReplyRelationAdapter extends BaseQuickAdapter<RelationList, BaseViewHolder> {
    public ChagedReplyRelationAdapter(int layoutResId, @Nullable List<RelationList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RelationList item) {
        helper.setText(R.id.number, "编号：" + item.getCode());
        if (!item.getCode().isEmpty()) {
            helper.setText(R.id.send_people, "下发人：：" + item.getCode());
        } else {
            helper.setText(R.id.send_people, "下发人：：");
        }
        if (!item.getAcceptPersonName().isEmpty()) {
            helper.setText(R.id.chaged_people, "整改负责人：" + item.getAcceptPersonName());
        } else {
            helper.setText(R.id.chaged_people, "整改负责人：");
        }

    }
}
