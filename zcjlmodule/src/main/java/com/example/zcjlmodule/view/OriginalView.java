package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.OriginalZcBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
/**
 * description: 原始勘丈表的
 * @author lx
 * date: 2018/10/29 0029 上午 11:15
 * update: 2018/10/29 0029
 * version:
 * activity OriginalZcActivity
 */
public interface OriginalView extends BaseView {
    void getData(ArrayList<OriginalZcBean> list);
}
