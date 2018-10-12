package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.model.PayCheckModel;
import com.example.zcjlmodule.view.PayCheckView;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class PayCheckPresenter extends BasePresenters<PayCheckView> {
    private PayCheckModel.Model model;
    public PayCheckPresenter() {

    }
    //在Activity调用的方法
    public void init(String name) {
        model = new PayCheckModel.PayCheckModelPml();
        String str = model.init(name);
        mView.getdata(str);
    }
}
