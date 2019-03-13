package com.example.administrator.newsdf.pzgc.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.example.administrator.newsdf.pzgc.bean.LastmonthBean;
import com.example.administrator.newsdf.pzgc.bean.TodayBean;
import com.example.administrator.newsdf.pzgc.bean.TotalBean;
import com.example.baselibrary.adapter.multiitem.MultipleItemRvAdapter;

import java.util.List;
/**
* @author lx
* @data :2019/3/12 0012
* @描述 :
*@see HometaskActivity
*/
public class HometasksAdapter extends MultipleItemRvAdapter<Object, BaseViewHolder> {
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;

    public HometasksAdapter(@Nullable List<Object> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Object object) {
        //返回对应的viewType
        if (object instanceof TotalBean) {
            return TYPE_ONE;
        } else if (object instanceof TodayBean) {
            return TYPE_TWO;
        } else if (object instanceof LastmonthBean) {
            return TYPE_THREE;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new HometaskaddupAdapter());
        mProviderDelegate.registerProvider(new HometasktodaytaskAdapter());
        mProviderDelegate.registerProvider(new HometasklastAdapter());
    }
}
