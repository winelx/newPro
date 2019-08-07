package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProListBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.List;

public class ProgrammeListAdapter extends BaseQuickAdapter<ProListBean, BaseViewHolder> {
    public ProgrammeListAdapter(int layoutResId, @Nullable List<ProListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProListBean item) {
        helper.setText(R.id.item_title, item.getSpecialItemDelName());
        helper.setText(R.id.submitpersonname, "编制人：" + item.getCreatePerson());
        helper.setText(R.id.dealdate, "申报日期：" + Dates.stampToDate(item.getSubmitDate()).substring(0, 10));
        helper.setText(R.id.specialitembasename, "所属类型：" + item.getSpecialItemBaseName());
        helper.setText(R.id.orgname, "所属标段：" + item.getOrgName());
        TextView view = helper.getView(R.id.solvepeople);
        view.setVisibility(View.GONE);
    }
}
