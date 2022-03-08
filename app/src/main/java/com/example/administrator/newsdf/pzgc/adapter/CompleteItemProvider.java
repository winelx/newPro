package com.example.administrator.newsdf.pzgc.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * 创建日期：2019/3/9 0009
 * 描述：已办事项
 * {@link }
 */
public class CompleteItemProvider extends BaseItemProvider<CompleteBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return NoticeAdapter.TYPE_THREE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_item_complete;
    }

    @Override
    public void convert(BaseViewHolder helper, CompleteBean bean, int position) {
        helper.setText(R.id.complete_modelname, bean.getModelName() + "(" + bean.getModelCode() + ")" + "已处理");
        helper.setText(R.id.complete_title, bean.getModelName());
        helper.setText(R.id.complete_result, "处理结果：" + bean.getDealResult());
        helper.setText(R.id.complete_data, "处理时间：" + bean.getDealDate());
        helper.setText(R.id.complete_datatime, bean.getSendDate().substring(0, 10));
    }
}
