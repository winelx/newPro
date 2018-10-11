package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.view.ModuleMainView;
import com.example.zcjlmodule.model.ModuleMainBaseViewIpm;


import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class ModuleMainPresenter extends BasePresenters<ModuleMainView> {
    private ModuleMainBaseViewIpm.ModuleMainBaseView model;

    public ModuleMainPresenter() {

    }

    public void register(String name, String pass) {
        model = new ModuleMainBaseViewIpm.ModuleMainBaseViewIPml();
        String str = model.register(name, pass);
        mView.getdata(str);
    }
}
