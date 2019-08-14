package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;

import java.util.ArrayList;
import java.util.List;

public class ScircuititemAdaptyer extends BaseQuickAdapter<ProDetails.RecordListBean, BaseViewHolder> {

    public ScircuititemAdaptyer(int layoutResId, @Nullable List<ProDetails.RecordListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProDetails.RecordListBean item) {
        String org = item.getOwnOrg();
     TextView des= helper.getView(R.id.describe);
        if ("0".equals(org)) {
            helper.setText(R.id.username, "编制人：" + item.getDealPerson());
            helper.setText(R.id.datatime, item.getDealDate().substring(0, 10));
            des.setVisibility(View.GONE);
        } else {
            helper.setText(R.id.username, "审核人：" + item.getDealPerson());
            helper.setText(R.id.describe, "审核意见：" + item.getDealOpin());
            helper.setText(R.id.datatime, item.getDealDate().substring(0, 10));
        }

    }
}
