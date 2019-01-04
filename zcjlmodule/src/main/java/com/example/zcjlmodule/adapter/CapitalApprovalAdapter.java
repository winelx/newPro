package com.example.zcjlmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.CapitalBean;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/26 0026.
 * @description:
 */

public class CapitalApprovalAdapter extends BaseQuickAdapter<CapitalBean, BaseViewHolder> {

    public CapitalApprovalAdapter(int layoutResId, @Nullable List<CapitalBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CapitalBean item) {
        //期数
        helper.setText(R.id.adapter_capital_number, item.getPeriod() + "");
        //金额
        helper.setText(R.id.adapter_capital_price, item.getTotalApplyAmount());
        //创建人
        helper.setText(R.id.adapter_capital_name, item.getCreateBy().getRealname());
//        //创建时间
        if (item.getUpdateDate()!=null){
            helper.setText(R.id.adapter_capital_data, item.getUpdateDate().substring(0,10));
        }else {
            helper.setText(R.id.adapter_capital_data, item.getUpdateDate());
        }


    }
}