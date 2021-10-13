package com.example.administrator.yanghu.pzgc.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;


import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.bean.LastmonthBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

public class HometasklastAdapter extends BaseItemProvider<LastmonthBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HometasksAdapter.TYPE_THREE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_hometask_last;
    }

    @Override
    public void convert(BaseViewHolder holder, LastmonthBean bean, int position) {
        String string = bean.getNoticeCount() + "";
        //存在问题
        holder.setText(R.id.problem_number, setText(mContext, "问题共计：" + string + "项", string.length()));
        //公司名称
        holder.setText(R.id.problem_name, bean.getName());
        //完成任务
        holder.setText(R.id.problem_complete, "已完成：" + bean.getFinish());
        //超期完成
        holder.setText(R.id.problem_overtime, "超期完成：" + bean.getYesOverdueFinish());
        //未整改数
        holder.setText(R.id.problem_chaged, "未完成整改：" + bean.getNotFinish());
        //未整改
        holder.setText(R.id.problem_nochaged, "未整改：" + bean.getNoFinish());

    }

    public static SpannableString setText(Context mContext, String text, int lenght) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), 5,
                lenght + 5,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
