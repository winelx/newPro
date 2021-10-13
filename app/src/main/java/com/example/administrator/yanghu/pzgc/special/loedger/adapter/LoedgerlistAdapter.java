package com.example.administrator.yanghu.pzgc.special.loedger.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.special.loedger.activity.LoedgerlistActivity;
import com.example.administrator.yanghu.pzgc.special.loedger.bean.LoedgerAllListbean;
import com.example.administrator.yanghu.pzgc.special.loedger.bean.LoedgerMineListbean;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.administrator.yanghu.pzgc.utils.SlantedTextView;

import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/7/31 0031 13:32
 * @说明 台账
 **/

public class LoedgerlistAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public LoedgerlistAdapter(int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        //角标控件
        SlantedTextView slante = helper.getView(R.id.inface_item_message);
        TextView solvepeople = helper.getView(R.id.solvepeople);
        LoedgerlistActivity activity = (LoedgerlistActivity) mContext;
        boolean lean = activity.getType();
        if (lean) {
            //我的
            LoedgerMineListbean bean = (LoedgerMineListbean) item;

            helper.setText(R.id.item_title, bean.getName());
            helper.setText(R.id.submitpersonname, "报批人：" + bean.getSubmitPersonName());
            helper.setText(R.id.specialitembasename, "所属类型：" + bean.getSpecialItemBaseName());
            helper.setText(R.id.orgname, "所属标段：" + bean.getOrgName());
            if (!TextUtils.isEmpty(bean.getReceivePerson())) {
                solvepeople.setText("待处理人：" + bean.getReceivePerson());
            } else {
                solvepeople.setText("待处理人：");
            }
            if (!TextUtils.isEmpty(bean.getSubmitDate())) {
                helper.setText(R.id.dealdate, "报批日期：" + Dates.stampToDate(bean.getSubmitDate()).substring(0, 10));
            } else {
                helper.setText(R.id.dealdate, "报批日期：");
            }
            //任务处理状态
            int isDeal = bean.getIsDeal();
            //我的显示样式
            if (isDeal == 1) {
                slante.setTextString("未处理");
                slante.setSlantedBackgroundColor(R.color.Orange);
            } else {
                slante.setTextString("已处理");
                slante.setSlantedBackgroundColor(R.color.finish_green);
            }
        } else {
            //全部
            LoedgerAllListbean bean = (LoedgerAllListbean) item;
            helper.setText(R.id.item_title, bean.getName());
            helper.setText(R.id.submitpersonname, "报批人：" + bean.getSubmitPersonName());
            helper.setText(R.id.specialitembasename, "所属类型：" + bean.getSpecialItemBaseName());
            helper.setText(R.id.orgname, "所属标段：" + bean.getOrgName());
            if (!TextUtils.isEmpty(bean.getSubmitDate())) {
                helper.setText(R.id.dealdate, "报批日期：" + Dates.stampToDate(bean.getSubmitDate()).substring(0, 10));
            } else {
                helper.setText(R.id.dealdate, "报批日期：");
            }
            //当前
            int isDeal = bean.getStatus();
            solvepeople.setText("待处理人：" + bean.getReceivePerson());
            //全部的显示样式
            if (isDeal == 4) {
                slante.setTextString(" 审核通过");
                slante.setSlantedBackgroundColor(R.color.finish_green);
            } else if (isDeal == 2) {
                slante.setTextString("审核中");
                slante.setSlantedBackgroundColor(R.color.Orange);
            } else if (isDeal == 3) {
                slante.setTextString("打回");
                slante.setSlantedBackgroundColor(R.color.red);
            } else if (isDeal == 0) {
                slante.setTextString("保存");
                slante.setSlantedBackgroundColor(R.color.gray);

            }
        }
    }
}
