package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedReplyRelationActivity;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：关联整改通知单
 * {@link  ChagedReplyRelationActivity}
 */
public class ChagedReplyRelationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChagedReplyRelationAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      /*  helper.setText(R.id.content,item);
*/
    }
}
