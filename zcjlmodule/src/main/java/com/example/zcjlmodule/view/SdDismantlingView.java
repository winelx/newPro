package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.SdDismantlingBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * description: 征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 2:31
 *         update: 2018/10/19 0019
 *         version:
 *         activity  StandardDismantlingZcActivity
 */
public interface SdDismantlingView extends BaseView {
        void getdata(ArrayList<SdDismantlingBean> list);
        void onError();
}