package com.example.administrator.newsdf.pzgc.Adapter;



import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TodayBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;


public class HometasktodaytaskAdapter extends BaseItemProvider<TodayBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HometasksAdapter.TYPE_TWO;
    }

    @Override
    public int layout() {
        return R.layout.adapter_hometask_todaytask;
    }

    @Override
    public void convert(BaseViewHolder holder, TodayBean bean, int position) {
        holder.setText(R.id.total_name, bean.getfOrgName());
        holder.setText(R.id.total_numer, bean.getFinishCount());
    }
}
