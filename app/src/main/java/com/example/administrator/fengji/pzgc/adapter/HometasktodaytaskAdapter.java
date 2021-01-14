package com.example.administrator.fengji.pzgc.adapter;



import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.bean.TodayBean;
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
        holder.setText(R.id.today_name, bean.getfOrgName());
        holder.setText(R.id.today_number, bean.getFinishCount());
    }
}
