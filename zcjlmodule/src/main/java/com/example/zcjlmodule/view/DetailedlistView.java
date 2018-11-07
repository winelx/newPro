package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.PayDetailedlistBean;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:支付清册
 * activity  PayDetailedlistZcActivity
 */


public interface DetailedlistView extends BaseView {
    void getdata(List<PayDetailedlistBean> str);
    void onerror();
}
