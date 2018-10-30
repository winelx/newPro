package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.PayCheckZcBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
/**
 * description: 支付清册核查
 * @author lx
 * date: 2018/10/29 0029 上午 11:16
 * update: 2018/10/29 0029
 * version:
 * activity PayCheckZcActivity
 */
public interface PayCheckView extends BaseView {
    void getdata(ArrayList<PayCheckZcBean> list);
    void onerror();
}
