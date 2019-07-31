package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailRecord;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;


/**
 * @Author lx
 * @创建时间  2019/7/31 0031 16:16
 * @说明 详情记录布局
 * @see LoedgerDetailAdapter
 **/

public class DetailRecordAdapter   extends BaseItemProvider<DetailRecord, BaseViewHolder> {
    @Override
    public int viewType() {
        return LoedgerDetailAdapter.TYPE_RECORD;
    }

    @Override
    public int layout() {
        return R.layout.adapter_loedgerdetails_type_record;
    }

    @Override
    public void convert(BaseViewHolder helper, DetailRecord data, int position) {

    }
}
