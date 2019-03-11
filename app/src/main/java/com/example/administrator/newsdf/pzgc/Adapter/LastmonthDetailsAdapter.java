package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.LastmonthDetailsBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 上月整改单标段详情
 * @see HomeTaskDetailsAdapter
 */
public class LastmonthDetailsAdapter extends BaseItemProvider<LastmonthDetailsBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeTaskDetailsAdapter.TYPE_THREE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_chaged_org_details;
    }

    @Override
    public void convert(BaseViewHolder helper, LastmonthDetailsBean data, int position) {
        String string = (position + 1) + "";
        helper.setText(R.id.problem_number, setText(mContext, "问题共计：" + string + "项", string.length()));
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
