package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;


import java.util.List;

public class ProgrammedetailscircuitAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {


    public ProgrammedetailscircuitAdapter(int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        //修改布局高度
        RelativeLayout mView = helper.getView(R.id.scircuit_content);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mView.getLayoutParams();
        params.height = Utils.dp2px(mContext, 80);
        mView.setLayoutParams(params);

    }
}
