package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.model.OriginalModel;
import com.example.zcjlmodule.view.OriginalView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

/**
 * description: 原始勘丈表的
 *
 * @author lx
 *         date: 2018/10/29 0029 上午 11:15
 *         update: 2018/10/29 0029
 *         version:
 *         activity OriginalZcActivity
 */
public class OriginalPresenter extends BasePresenters<OriginalView> {
    private OriginalModel.Model model;

    public void getdata(String orgId,int page) {
        model = new OriginalModel.OriginalModelPml();
        model.getdata(orgId, page, new OriginalModel.OnClicklister() {
            @Override
            public void onSuccess(ArrayList<OriginalZcBean> list) {
                mView.onSuccess(list);
            }

            @Override
            public void onError() {
                mView.onError();
            }
        });
    }
}
