package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;

import java.util.List;

public class ExternalCheckListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ExternalCheckListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.content_re);
        helper.setText(R.id.check_title, "标题");
        helper.setText(R.id.check_user, "检查人：");
        helper.setText(R.id.check_tiem, "检查日期：");
        helper.setText(R.id.check_bid, "所属标段：");
        helper.setText(R.id.check_org, "所属组织：");
        helper.setText(R.id.score, "得分：");
        helper.setText(R.id.check_status, "单据状态：");
        helper.setText(R.id.handle_user, "待处理人：");
        SlantedTextView slanted = helper.getView(R.id.slanted);
        slanted.setTextString("未上报");
        slanted.setSlantedBackgroundColor(R.color.graytext);
        SwipeMenuLayout swipeMenuLayout = helper.getView(R.id.swipmenu);
        swipeMenuLayout.setIos(true).setLeftSwipe(true).setSwipeEnable(true);
//        if (item.getStatus() == 1) {
//            slanted.setVisibility(View.GONE);
//        }
    }
}
