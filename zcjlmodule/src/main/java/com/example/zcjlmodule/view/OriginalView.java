package com.example.zcjlmodule.view;

import com.example.zcjlmodule.bean.OriginalZcBean;

import java.math.BigDecimal;
import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;


/**
 * description: 原始勘丈表的
 *
 * @author lx
 *         date: 2018/10/31 0031 下午 2:51
 *         activity OriginalZcActivity
 */
public interface OriginalView extends BaseView {

    /**
     * description: 请求成功
     *
     * @param list       返回数据
     * @param totalPrice 当前列表请求的数据的总价格
     * @author lx
     * date: 2018/10/31 0031 下午 2:52
     */
    void onSuccess(ArrayList<OriginalZcBean> list, BigDecimal totalPrice);

    /**
     * description: 请求失败
     *
     * @author lx
     * date: 2018/10/31 0031 下午 2:52
     */
    void onError();
}
