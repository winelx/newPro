package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.UnknitstandardBean;
import com.example.zcjlmodule.model.UnknitstandardModel;
import com.example.zcjlmodule.view.UnknitstandardView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:  标准分解
 * activity UnknitstandardActivity
 */

public class UnknitstandardPresenter extends BasePresenters<UnknitstandardView> {
    private UnknitstandardModel.Model model;

    public void getdata() {
        model = new UnknitstandardModel.UnknitstandardPresenterIpm();
        model.getdata("s", new UnknitstandardModel.OnClicklister() {
            @Override
            public void Callback(ArrayList<UnknitstandardBean> list) {
                mView.getdata(list);
            }
        });
    }
}
