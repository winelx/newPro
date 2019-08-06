package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerlistActivity;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.LoedgerListbean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;

import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/7/31 0031 13:32
 * @说明 台账
 **/

public class LoedgerlistAdapter extends BaseQuickAdapter<LoedgerListbean, BaseViewHolder> {
    public LoedgerlistAdapter(int layoutResId, @Nullable List<LoedgerListbean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, LoedgerListbean item) {
        helper.setText(R.id.item_title, item.getName());
        helper.setText(R.id.submitpersonname, "报批人：" + item.getSubmitPersonName());
        helper.setText(R.id.dealdate, "报批日期：" + Dates.stampToDate(item.getSubmitDate()).substring(0, 10));
        helper.setText(R.id.specialitembasename, "所属类型：" + item.getSpecialItemBaseName());
        helper.setText(R.id.orgname, "所属标段:" + item.getOrgName());
        //当前
        int isDeal = item.getIsDeal();
        SlantedTextView slante = helper.getView(R.id.inface_item_message);
        //角标控件
        TextView solvepeople = helper.getView(R.id.solvepeople);
        LoedgerlistActivity activity = (LoedgerlistActivity) mContext;
        boolean lean = activity.getType();
        //根据状态处理现实样式
        if (lean) {
            //我的显示样式
            if (isDeal == 1) {
                slante.setTextString("未处理");
                slante.setSlantedBackgroundColor(R.color.Orange);
                solvepeople.setText("待处理人：" + item.getReceivePerson());
            } else {
                slante.setTextString("已处理");
                slante.setSlantedBackgroundColor(R.color.finish_green);
                solvepeople.setVisibility(View.GONE);
            }
        } else {
            //全部的显示样式
            if (isDeal == 4) {
                slante.setTextString("审核中");
                slante.setSlantedBackgroundColor(R.color.Orange);
                solvepeople.setText("待处理人：" + item.getReceivePerson());
            } else if (isDeal == 2) {
                slante.setTextString("审核通过");
                slante.setSlantedBackgroundColor(R.color.finish_green);
            } else if (isDeal == 3) {
                slante.setTextString("打回");
                slante.setSlantedBackgroundColor(R.color.red);
                solvepeople.setVisibility(View.GONE);
                solvepeople.setText("待处理人：" + item.getReceivePerson());
            } else if (isDeal == 0) {
                slante.setTextString("保存");
                slante.setSlantedBackgroundColor(R.color.gray);
                solvepeople.setVisibility(View.GONE);
            }
        }

    }
}
