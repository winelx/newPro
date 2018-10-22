package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.PayCheckZcBean;

import java.lang.reflect.Array;
import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface PayCheckView extends BaseView {
    void getdata(ArrayList<PayCheckZcBean> list);
}
