package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;

import java.util.List;

public class ProgrammeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProgrammeListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView view = helper.getView(R.id.solvepeople);
        view.setVisibility(View.GONE);
    }
}
