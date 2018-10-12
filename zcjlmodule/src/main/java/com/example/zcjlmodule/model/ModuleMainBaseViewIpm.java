package com.example.zcjlmodule.model;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class ModuleMainBaseViewIpm {

   public interface ModuleMainBaseView extends BaseView {
        String register(String user, String pass);
    }

    public static class ModuleMainBaseViewIPml implements ModuleMainBaseView {
        @Override
        public String register(String user, String pass) {
            if ("name".equals(user) && "pass".equals(pass)) {
                return "成功";
            } else {
                return "失败";
            }
        }
    }
}
