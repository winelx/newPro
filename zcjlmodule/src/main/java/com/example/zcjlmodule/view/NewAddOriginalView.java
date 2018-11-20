package com.example.zcjlmodule.view;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * @author lx
 * @Created by: 2018/11/2 0002.
 * @description:
 * activity :NewAddOriginalZcActivity
 */

public interface NewAddOriginalView  extends BaseView {
    /**
     * 请求成功
     */
    void OnSuccess(String str,String number);

    /**
     * 请求失败
     */
    void OnError();
}
