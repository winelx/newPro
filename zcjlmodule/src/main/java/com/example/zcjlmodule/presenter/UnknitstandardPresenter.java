package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.UnknitstandardBean;
import com.example.zcjlmodule.model.UnknitstandardModel;
import com.example.zcjlmodule.view.UnknitstandardView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description: 标准分解
 * activity UnknitstandardActivity
 */

public class UnknitstandardPresenter extends BasePresenters<UnknitstandardView> {
    private UnknitstandardModel.Model model;

    public void getdata(String orgid, int page) {
        model = new UnknitstandardModel.UnknitstandardPresenterIpm();
        model.getdata(orgid, page, new UnknitstandardModel.OnClicklister() {
            @Override
            public void onSuccess(ArrayList<UnknitstandardBean> list) {
                mView.onSuccess(list);
            }
            @Override
            public void onError() {
                mView.onError();
            }
        });
    }
}
