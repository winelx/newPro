package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.SdDismantlingBean;
import com.example.zcjlmodule.model.SdDismantlingModel;
import com.example.zcjlmodule.view.SdDismantlingView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;


/**
 * description: 征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 2:31
 *         update: 2018/10/19 0019
 *         version:
 *         activity  StandardDismantlingZcActivity
 */
public class SdDismantlingPresenter extends BasePresenters<SdDismantlingView> {
    private SdDismantlingModel.Model model;

    public void getdata(String orgId, int page) {
        model = new SdDismantlingModel.SdDismantlingModelIpm();
        model.init(orgId, page, new SdDismantlingModel.OnClickListener() {
            @Override
            public void onSuccess(ArrayList<SdDismantlingBean> list) {
                //请求成功
                mView.onSuccess(list);
            }
            @Override
            public void onError() {
                //请求失败
                mView.onError();
            }
        });
    }
}
