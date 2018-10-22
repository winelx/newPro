package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.SdDismantlingBean;
import com.example.zcjlmodule.model.SdDismantlingModel;
import com.example.zcjlmodule.view.SdDismantlingView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class SdDismantlingPresenter extends BasePresenters<SdDismantlingView> {
    private SdDismantlingModel.Model model;

    public void getdata() {
        model = new SdDismantlingModel.SdDismantlingModelIpm();
        model.init("", new SdDismantlingModel.OnClickListener() {
            @Override
            public void callBack(ArrayList<SdDismantlingBean> list) {
                mView.getdata(list);
            }
        });
    }
}
