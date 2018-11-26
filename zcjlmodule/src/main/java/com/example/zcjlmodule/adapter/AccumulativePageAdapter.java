package com.example.zcjlmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.PeriodListBean;

import java.util.List;


/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:申请单/拨款审批单(累计)
 * @param:AccumulativePageFragment
 */

public class AccumulativePageAdapter extends BaseQuickAdapter<PeriodListBean, BaseViewHolder> {

    public AccumulativePageAdapter(int layoutResId, @Nullable List<PeriodListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PeriodListBean item) {
        helper.setText(R.id.item_headquarters,item.getHeadquarterName());
        helper.setText(R.id.item_price,item.getThisPeriodApplyAmount()+"");
    }
}
