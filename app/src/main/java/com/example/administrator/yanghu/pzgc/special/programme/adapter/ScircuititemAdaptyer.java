package com.example.administrator.yanghu.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.special.programme.bean.ProDetails;
import com.example.administrator.yanghu.pzgc.utils.Dates;

import java.util.List;

public class ScircuititemAdaptyer extends BaseQuickAdapter<ProDetails.RecordListBean, BaseViewHolder> {

    public ScircuititemAdaptyer(int layoutResId, @Nullable List<ProDetails.RecordListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProDetails.RecordListBean item) {
        String org = item.getOwnOrg();
        TextView des = helper.getView(R.id.describe);
        if ("0".equals(org)) {
            helper.setText(R.id.username, "编制人：" + item.getDealPerson());
            helper.setText(R.id.datatime, item.getDealDate());
            des.setVisibility(View.GONE);
        } else {
            helper.setText(R.id.username, "审核人：" + item.getDealPerson());
            String msp = "审核意见：" + item.getDealResult() + "  " + item.getDealOpin();
            int color = 0;
            if ("同意".equals(item.getDealResult())) {
                color = R.color.finish_green;
            } else if ("打回".equals(item.getDealResult())) {
                color = R.color.red;
            } else {
                color = R.color.black;
            }
            helper.setText(R.id.describe, Dates.setText(mContext, msp, 5, 7, color));
            helper.setText(R.id.datatime, item.getDealDate());
        }

    }
}
