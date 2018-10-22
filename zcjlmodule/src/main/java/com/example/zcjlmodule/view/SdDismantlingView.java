package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.PayCheckZcBean;
import com.example.zcjlmodule.bean.SdDismantlingBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public interface SdDismantlingView extends BaseView {
        void getdata(ArrayList<SdDismantlingBean> list);
}