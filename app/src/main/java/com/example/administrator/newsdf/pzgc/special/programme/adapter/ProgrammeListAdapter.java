package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeListActivity;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProListBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;

import java.util.List;

public class ProgrammeListAdapter extends BaseQuickAdapter<ProListBean, BaseViewHolder> {
    public ProgrammeListAdapter(int layoutResId, @Nullable List<ProListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProListBean item) {
        ProgrammeListActivity activity = (ProgrammeListActivity) mContext;
        boolean lean = activity.getType();
        helper.setText(R.id.item_title, item.getSpecialItemDelName());
        helper.setText(R.id.submitpersonname, "编制人：" + item.getCreatePerson());
        helper.setText(R.id.dealdate, "申报日期：" + Dates.stampToDate(item.getSubmitDate()).substring(0, 10));
        helper.setText(R.id.specialitembasename, "所属类型：" + item.getSpecialItemBaseName());
        helper.setText(R.id.orgname, "所属标段：" + item.getOrgName());
        TextView view = helper.getView(R.id.solvepeople);
        view.setVisibility(View.GONE);
        SlantedTextView slante = helper.getView(R.id.inface_item_message);
        int isDeal = item.getIsDeal();
        if (lean) {
            //我的显示样式
            if (isDeal == 1) {
                slante.setTextString("未处理");
                slante.setSlantedBackgroundColor(R.color.Orange);
            } else {
                slante.setTextString("已处理");
                slante.setSlantedBackgroundColor(R.color.finish_green);
            }
        } else {
//全部的显示样式
            if (isDeal == 4) {
                slante.setTextString("审核中");
                slante.setSlantedBackgroundColor(R.color.Orange);
            } else if (isDeal == 2) {
                slante.setTextString("完成");
                slante.setSlantedBackgroundColor(R.color.finish_green);
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
