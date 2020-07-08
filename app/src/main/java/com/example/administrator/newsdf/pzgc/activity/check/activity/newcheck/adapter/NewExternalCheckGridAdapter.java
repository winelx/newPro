package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;
import com.example.baselibrary.utils.log.LogUtil;

import java.util.List;

public class NewExternalCheckGridAdapter extends BaseQuickAdapter<CheckNewBean.scorePane, BaseViewHolder> {
    public NewExternalCheckGridAdapter(int layoutResId, @Nullable List<CheckNewBean.scorePane> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckNewBean.scorePane item) {
        RelativeLayout content = helper.getView(R.id.chek_item_re);
        ImageView angleofthe = helper.getView(R.id.angleofthe);
        helper.setText(R.id.text_item, (helper.getLayoutPosition() + 1) + "");
        if (TextUtils.isEmpty(item.getScore())) {
            content.setBackgroundResource(R.color.persomal_text);
        } else {
            /* 绿色 橙色*/
            int compare = item.getScore().compareTo(item.getStandardScore());
            Integer checkScore = item.getCheckScore() == null ? 0 : item.getCheckScore();
            if (checkScore < 0 || compare < 0) {
                //被扣分
                content.setBackgroundResource(R.color.Orange);
            } else {
                content.setBackgroundResource(R.color.green);
            }
            if (item.getGenerate() != null) {
                Integer generate = item.getGenerate().intValue();
                if (generate == 1) {
                    angleofthe.setVisibility(View.GONE);
                } else if (generate==2){
                    angleofthe.setVisibility(View.VISIBLE);
                    angleofthe.setBackgroundResource(R.mipmap.triangle_red);
                }
            } else {
                angleofthe.setVisibility(View.GONE);
            }
        }
    }
}
