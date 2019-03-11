package com.example.administrator.newsdf.pzgc.Adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TodayDetailsBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 今日完成任务标段详情
 * @see HomeTaskDetailsAdapter
 */
public class TodayDetailsAdapter extends BaseItemProvider<TodayDetailsBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeTaskDetailsAdapter.TYPE_TWO;
    }

    @Override
    public int layout() {
        return R.layout.adapter_total_org_details;
    }

    @Override
    public void convert(BaseViewHolder helper, TodayDetailsBean data, int position) {

    }
}
