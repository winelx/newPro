package com.example.zcjlmodule.model;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class PayCheckModel {
    public interface Model extends BaseView {
        String init(String user);
    }

    public static class PayCheckModelPml implements Model {
        @Override
        public String init(String pass) {
           return "12";
        }
    }
}
