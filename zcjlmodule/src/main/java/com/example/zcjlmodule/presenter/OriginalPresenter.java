package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.model.OriginalModel;
import com.example.zcjlmodule.view.OriginalView;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public class OriginalPresenter extends BasePresenters<OriginalView> {
    private OriginalModel.Model model;

    public void getdata() {
        model.getData("s", new OriginalModel.Model.OnClickListener() {
            @Override
            public void onComple(int string) {
                mView.getData();
            }
        });
    }
}
