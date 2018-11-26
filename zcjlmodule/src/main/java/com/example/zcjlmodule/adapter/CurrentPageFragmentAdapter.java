package com.example.zcjlmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.CurrentApplyBean;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:申请单/拨款审批单(本期)
 * @fragment:CurrentPageFragment
 */


public class CurrentPageFragmentAdapter extends BaseQuickAdapter<CurrentApplyBean, BaseViewHolder> {

    public CurrentPageFragmentAdapter(int layoutResId, @Nullable List<CurrentApplyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CurrentApplyBean item) {
        helper.setText(R.id.item_headquarters,item.getHeadquarterName());
        helper.setText(R.id.item_price,item.getToThisPeriodAmount());
    }
}

