package com.example.administrator.newsdf.pzgc.Adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TotalDetailsBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 累计完任务标段详情
 * @see HomeTaskDetailsAdapter
 */
public class TotalDetailsAdapter extends BaseItemProvider<TotalDetailsBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeTaskDetailsAdapter.TYPE_ONE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_total_org_details;
    }

    @Override
    public void convert(BaseViewHolder helper, TotalDetailsBean data, int position) {

    }
}
