package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.model.DetailedlistModel;
import com.example.zcjlmodule.view.DetailedlistView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:支付清册 activity  PayDetailedlistZcActivity
 */

public class DetailedlistPresenter extends BasePresenters<DetailedlistView> {
    private DetailedlistModel.DetailedlistModelIpm model;

    public void register(String orgId, int page) {
        //实例model
        model = new DetailedlistModel.DetailedlistModelIpm();
        //请求数据并返回结果
        model.getData(orgId, page, new DetailedlistModel.Model.OnClickListener() {
            @Override
            public void onComple(ArrayList<PayDetailedlistBean> list) {
                mView.getdata(list);
            }
            @Override
            public void onError() {
                //请求失败
                mView.onerror();
            }
        });

    }
}
