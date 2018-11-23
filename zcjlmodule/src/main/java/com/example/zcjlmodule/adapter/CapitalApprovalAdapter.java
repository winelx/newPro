package com.example.zcjlmodule.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/23 0023.
 * @description:资金审批单适配器
 * @param fragment: CapitalApprovalFFragment
 */

public class CapitalApprovalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CapitalApprovalAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    helper.setText(R.id.adapter_capital_data,item);
    }
}
