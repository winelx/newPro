package com.example.administrator.yanghu.pzgc.special.loedger.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @Author lx
 * @创建时间  2019/8/7 0007 9:39
 * @说明 分割线
 **/

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
