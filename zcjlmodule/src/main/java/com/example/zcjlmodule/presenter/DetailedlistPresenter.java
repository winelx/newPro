package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.model.DetailedlistModel;
import com.example.zcjlmodule.model.ModuleMainBaseViewIpm;
import com.example.zcjlmodule.view.DetailedlistView;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:支付清册
 */

public class DetailedlistPresenter extends BasePresenters<DetailedlistView> {
    private DetailedlistModel.DetailedlistModelIpm model;

    public DetailedlistPresenter() {

    }

    public void register(String orgId) {
        //实例model
        model = new DetailedlistModel.DetailedlistModelIpm();
        //请求数据并返回结果
        String str = model.register(orgId);
        //处理结果
        mView.getdata(str);
    }
}
