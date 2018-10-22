package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.OriginalZcBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public interface OriginalView extends BaseView {
    void getData(ArrayList<OriginalZcBean> list);
}
