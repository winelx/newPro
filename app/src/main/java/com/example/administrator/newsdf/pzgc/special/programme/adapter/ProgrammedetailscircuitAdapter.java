package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.administrator.newsdf.pzgc.utils.Utils;


import java.util.ArrayList;
import java.util.List;

public class ProgrammedetailscircuitAdapter extends BaseQuickAdapter<ProDetails.RecordListBean, BaseViewHolder> {
    private ScircuitcontentlistAdapter adapter;

    public ProgrammedetailscircuitAdapter(int layoutResId, @Nullable List<ProDetails.RecordListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, ProDetails.RecordListBean item) {
        //修改布局高度
        RelativeLayout mView = helper.getView(R.id.scircuit_content);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mView.getLayoutParams();
        params.height = Utils.dp2px(mContext, 80);
        mView.setLayoutParams(params);
        RecyclerView scircuit_content_list = helper.getView(R.id.scircuit_content_list);
        scircuit_content_list.setLayoutManager(new LinearLayoutManager(mContext));
        scircuit_content_list.setAdapter(adapter = new ScircuitcontentlistAdapter(R.layout.adapter_scircuit_content_list, new ArrayList<>()));

    }
}
