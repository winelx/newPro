package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.model.ModuleMainBaseViewIpm;
import com.example.zcjlmodule.view.ModuleMainView;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class ModuleMainPresenter extends BasePresenters<ModuleMainView> {
    private ModuleMainBaseViewIpm.ModuleMainBaseViewIPml model;

    public ModuleMainPresenter() {

    }

    public void register(String name, String pass) {

        model = new ModuleMainBaseViewIpm.ModuleMainBaseViewIPml();
      model.getData(name, pass, new ModuleMainBaseViewIpm.Model.OnClickListener() {
           @Override
           public void onComple(int string) {
               mView.getdata(string);
           }
       });

    }
}
