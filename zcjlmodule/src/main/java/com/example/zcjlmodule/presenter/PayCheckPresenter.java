package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.PayCheckZcBean;
import com.example.zcjlmodule.model.PayCheckModel;
import com.example.zcjlmodule.view.PayCheckView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class PayCheckPresenter extends BasePresenters<PayCheckView> {
    private PayCheckModel.Model model;
    //在Activity调用的方法
    public void init(String name) {
        model = new PayCheckModel.PayCheckModelPml();
       model.init(name, new PayCheckModel.OnClickListener() {
           @Override
           public void onComple(ArrayList<PayCheckZcBean> list) {
               mView.getdata(list);
           }
       });
    }
}
