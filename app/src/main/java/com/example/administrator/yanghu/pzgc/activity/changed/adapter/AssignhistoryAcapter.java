package com.example.administrator.yanghu.pzgc.activity.changed.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.administrator.yanghu.pzgc.bean.DeviceRecordBean;

import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/21 0021}
 * 描述：MainActivity

 */
public class AssignhistoryAcapter  extends BaseQuickAdapter<DeviceRecordBean,BaseViewHolder> {
    public AssignhistoryAcapter(int layoutResId, @Nullable List<DeviceRecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceRecordBean item) {

    }
}
