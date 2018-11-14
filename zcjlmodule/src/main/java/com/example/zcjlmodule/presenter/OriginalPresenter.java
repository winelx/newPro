package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.model.OriginalModel;
import com.example.zcjlmodule.view.OriginalView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BasePresenters;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * description: 原始勘丈表网络请求
 *
 * @author lx
 *         date: 2018/10/29 0029 上午 11:15
 *         update: 2018/10/29 0029
 *         version:
 *         activity OriginalZcActivity
 */
public class OriginalPresenter extends BasePresenters<OriginalView> {
    private OriginalModel.Model model;

    public void getdata(String orgId, int page, Map<String, String> map) {
        model = new OriginalModel.OriginalModelPml();
        model.getdata(orgId, page, map, new OriginalModel.OnClicklister() {
            @Override
            public void onSuccess(ArrayList<OriginalZcBean> list, String str) {
                mView.onSuccess(list,str);
            }

            @Override
            public void onError() {
                mView.onError();
            }
        });
    }
}
