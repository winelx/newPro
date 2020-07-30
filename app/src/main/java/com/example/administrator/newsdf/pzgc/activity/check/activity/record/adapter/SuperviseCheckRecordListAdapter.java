package com.example.administrator.newsdf.pzgc.activity.check.activity.record.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.bean.RecordlistBean;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;

import java.util.List;

/**
 * 说明：监督检查记录列表
 * 创建时间： 2020/7/30 0030 10:25
 *
 * @author winelx
 */
public class SuperviseCheckRecordListAdapter extends BaseQuickAdapter<RecordlistBean, BaseViewHolder> {
    public SuperviseCheckRecordListAdapter(int layoutResId, @Nullable List<RecordlistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordlistBean item) {
        helper.addOnClickListener(R.id.content_re);
        helper.setText(R.id.check_title, item.getCode());
        helper.setText(R.id.check_project, "项目名称：" + Utils.isNull(item.getOrgName()));
        helper.setText(R.id.check_time, "检查日期：" + Utils.isNull(item.getCheckDate()));
        helper.setText(R.id.check_bid, "检查组织：" + Utils.isNull(item.getCheckOrgName()));
        helper.setText(R.id.check_org, "检查部门：" + Utils.isNull(item.getCheckPart()));
        helper.setText(R.id.responsibilityPart, "责任部门：" + Utils.isNull(item.getResponsibilityPart()));
        helper.setText(R.id.check_user, "检查人：" + Utils.isNull(item.getCheckPersion()));
        helper.setText(R.id.becheckpersion, "被检查人：" + Utils.isNull(item.getBeCheckPersion()));
        SwipeMenuLayout swipmenu = helper.getView(R.id.swipmenu);
        SlantedTextView slanted = helper.getView(R.id.slanted);
        slanted.setVisibility(View.INVISIBLE);
        slanted.setSlantedBackgroundColor(R.color.unfinish_gray);
        swipmenu.setIos(true).setLeftSwipe(true).setSwipeEnable(true);
    }
}
