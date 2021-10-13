package com.example.administrator.yanghu.pzgc.special.loedger.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.special.loedger.bean.LoedgerRecordDetailBean;

import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 11:14
 * @说明
 **/

public class LoedgerRecordDetailAdapter extends BaseQuickAdapter<LoedgerRecordDetailBean, BaseViewHolder> {
    public LoedgerRecordDetailAdapter(int layoutResId, @Nullable List<LoedgerRecordDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LoedgerRecordDetailBean item) {
        //专家论证日期
        RelativeLayout demonstration_lin = helper.getView(R.id.demonstration_lin);
        //专家论证内容
        LinearLayout demonstration_content = helper.getView(R.id.demonstration_content);
        String isExpert = item.getIsExpert();
        //1是否需要专家认证{1：否；2：是}
        if ("否".equals(isExpert)) {
            helper.setText(R.id.demonstration_status, "否");
            demonstration_lin.setVisibility(View.GONE);
            demonstration_content.setVisibility(View.GONE);
        } else {
            helper.setText(R.id.demonstration_status, "是");
            demonstration_lin.setVisibility(View.VISIBLE);
            demonstration_content.setVisibility(View.VISIBLE);
        }
        //报批日期
        helper.setText(R.id.approval_data, item.getPlanApprovedDate());
        //一次报批
        helper.setText(R.id.approval_data_one, item.getApprovedDate1());
        //二次报批
        helper.setText(R.id.approval_data_second, item.getApprovedDate2());
        //批复日期
        helper.setText(R.id.replydate, item.getReplyDate());
        helper.setText(R.id.specialitemdelconstruction, item.getSpecialItemDelConstruction());
        helper.setText(R.id.specialitemdelargument, item.getSpecialItemDelArgument());
        helper.setText(R.id.parturl, item.getPartUrl());
        helper.setText(R.id.constructionname,item.getConstructionName());
        helper.setText(R.id.argumentname,item.getArgumentName());
        helper.setText(R.id.demonstration_text,item.getExpertDate());
    }

}
