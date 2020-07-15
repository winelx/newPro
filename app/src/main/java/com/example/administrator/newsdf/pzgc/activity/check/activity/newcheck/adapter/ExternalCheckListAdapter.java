package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity.ExternalCheckListActiviy;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ExternalCheckListBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;
import com.example.baselibrary.view.BaseDialog;

import java.util.List;

/**
 * 说明：
 * 创建时间： 2020/7/2 0002 10:47
 *
 * @author winelx
 * @see ExternalCheckListActiviy
 */
public class ExternalCheckListAdapter extends BaseQuickAdapter<ExternalCheckListBean, BaseViewHolder> {
    public ExternalCheckListAdapter(int layoutResId, @Nullable List<ExternalCheckListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExternalCheckListBean item) {
        helper.addOnClickListener(R.id.content_re);
        helper.addOnClickListener(R.id.item_delete);
        SwipeMenuLayout swipmenu = helper.getView(R.id.swipmenu);
        SlantedTextView slanted = helper.getView(R.id.slanted);
        slanted.setVisibility(View.INVISIBLE);
        helper.setText(R.id.check_title, Utils.isNull(item.getName()));
        helper.setText(R.id.check_user, "检查人：" + Utils.isNull(item.getCheckPersonName()));
        helper.setText(R.id.org_title,"工程类型："+ Utils.isNull(item.getWbsTaskTypeName()));
        helper.setText(R.id.check_tiem, "检查日期：" + Utils.isNull(item.getCheckDate().substring(0, 10)));
        helper.setText(R.id.check_bid, Dates.setText(mContext, 4, "所属标段：" + Utils.isNull(item.getOrgName()), R.color.black));
        helper.setText(R.id.check_org, Dates.setText(mContext, 4, "检查组织：" + Utils.isNull(item.getCheckOrgName()), R.color.black));
        helper.setText(R.id.score, Dates.setText(mContext, 3,"得分：" + Utils.isNull(item.getTotalSocre()),  R.color.red));
        //0：保存；2：待分公司核查；3：待集团核查；4：待分公司确认；5：待标段确认；6：已确认

        if (item.getStatus() == 0) {
            helper.setText(R.id.check_status, "单据状态：保存");
            slanted.setVisibility(View.VISIBLE);
            slanted.setTextString("未上报");
            slanted.setSlantedBackgroundColor(R.color.unfinish_gray);
            swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(true);
        } else if (item.getStatus() == 2) {
            swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(false);
            helper.setText(R.id.check_status, "单据状态：待分公司核查");
        } else if (item.getStatus() == 3) {
            swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(false);
            helper.setText(R.id.check_status, "单据状态：待集团核查");
        } else if (item.getStatus() == 4) {
            swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(false);
            helper.setText(R.id.check_status, "单据状态：待分公司确认");
        } else if (item.getStatus() == 5) {
            swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(false);
            helper.setText(R.id.check_status, "单据状态：待标段确认");
        } else if (item.getStatus() == 6) {
            swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(false);
            helper.setText(R.id.check_status, "单据状态：已确认");
        }
        helper.setText(R.id.handle_user, "待处理人：" + Utils.isNull(item.getDealPerson()));

    }
}
