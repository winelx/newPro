package com.example.administrator.newsdf.pzgc.Adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TotalBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;

/**
 * @author lx
 * @data :2019/3/12 0012
 * @描述 :
 * @see HometasksAdapter
 */
public class HometaskaddupAdapter extends BaseItemProvider<TotalBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HometasksAdapter.TYPE_ONE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_hometask_addup;
    }

    @Override
    public void convert(BaseViewHolder holder, TotalBean bean, int position) {
        holder.setText(R.id.total_name, bean.getfOrgName());
        holder.setText(R.id.total_numer, bean.getFinishCount());
    }
}
