package com.example.administrator.newsdf.pzgc.special.loedger.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerDetailsActivity;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailRecord;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailsOption;
import com.example.baselibrary.adapter.multiitem.MultipleItemRvAdapter;

import java.util.List;


/**
 * @Author lx
 * @创建时间 2019/7/31 0031 16:16
 * @说明
 * @see LoedgerDetailsActivity
 **/

public class LoedgerDetailAdapter extends MultipleItemRvAdapter<Object, BaseViewHolder> {
    public static final int TYPE_RECORD = 1;
    public static final int TYPE_OPTION = 2;
    public static final int TYPE_TAB = 3;

    public LoedgerDetailAdapter(@Nullable List<Object> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Object bean) {
        if (bean instanceof DetailRecord) {
            //审批单
            return TYPE_RECORD;
        } else if (bean instanceof DetailsOption) {
            //审批单详情内容
            return TYPE_OPTION;
        } else if (bean instanceof String) {
            //审批单详情项目明细
            return TYPE_TAB;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new DetailRecordAdapter());
        mProviderDelegate.registerProvider(new DetailsOptionAdapter());
        mProviderDelegate.registerProvider(new DetailsPartitionAdapter());
    }
}
