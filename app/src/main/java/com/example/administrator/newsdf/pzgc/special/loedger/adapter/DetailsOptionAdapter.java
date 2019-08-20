package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailsOption;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;


/**
 * @Author lx
 * @创建时间 2019/7/31 0031 16:15
 * @说明 详情选项布局
 * @see LoedgerDetailAdapter
 **/

public class DetailsOptionAdapter extends BaseItemProvider<DetailsOption, BaseViewHolder> {
    @Override
    public int viewType() {
        return LoedgerDetailAdapter.TYPE_OPTION;
    }

    @Override
    public int layout() {
        return R.layout.adapter_loedgerdetails_type_option;
    }

    @Override
    public void convert(BaseViewHolder helper, DetailsOption data, int position) {
        helper.addOnClickListener(R.id.content);
        helper.setText(R.id.option_title, data.getSpecialItemDelName());
        helper.setText(R.id.option_content, data.getPartUrl());
    }
}
