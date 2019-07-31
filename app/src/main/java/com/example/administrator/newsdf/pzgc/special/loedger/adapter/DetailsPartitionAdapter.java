package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

public class DetailsPartitionAdapter extends BaseItemProvider<String, BaseViewHolder> {
    @Override
    public int viewType() {
        return LoedgerDetailAdapter.TYPE_TAB;
    }

    @Override
    public int layout() {
        return R.layout.base_partition_line;
    }

    @Override
    public void convert(BaseViewHolder helper, String str, int position) {
        helper.setText(R.id.content, str);
    }
}
