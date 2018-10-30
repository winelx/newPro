package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.PayCheckZcBean;
import com.example.zcjlmodule.model.PayCheckModel;
import com.example.zcjlmodule.view.PayCheckView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

/**
 * description: 支付清册核查
 *
 * @author lx
 *         date: 2018/10/29 0029 上午 11:16
 *         update: 2018/10/29 0029
 *         version:
 *         activity PayCheckZcActivity
 */
public class PayCheckPresenter extends BasePresenters<PayCheckView> {
    private PayCheckModel.Model model;

    //在Activity调用的方法
    public void init(String standardId, int page) {
        model = new PayCheckModel.PayCheckModelPml();
        model.init(standardId, new PayCheckModel.OnClickListener() {
            @Override
            public void onComple(ArrayList<PayCheckZcBean> list) {
                mView.getdata(list);
            }
            @Override
            public void onerror() {
                //请求失败
                mView.onerror();
            }
        });
    }
}
