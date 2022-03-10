package com.example.administrator.yanghu.pzgc.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.bean.AgencyBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * 创建日期：2019/3/9 0009
 * 描述：代办事项
 * {@link NoticeAdapter}
 */
public class AgencyItemProvider extends BaseItemProvider<AgencyBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return NoticeAdapter.TYPE_TWO;
    }

    @Override
    public int layout() {
        return R.layout.adapter_item_agency;
    }

    @Override
    public void convert(BaseViewHolder helper, AgencyBean bean, int position) {
        helper.setText(R.id.agency_content, "你有一条" + bean.getModelName() + "(" + bean.getModelCode() + ")" + "需要处理。");
        helper.setText(R.id.agency_data, bean.getSendDate().substring(0,10));
        helper.setText(R.id.agency_title, bean.getModelName());
    }
}
