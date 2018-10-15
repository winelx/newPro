package com.example.zcjlmodule.model;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public class OriginalModel {

    public interface Model extends BaseView {
        /**
         * 获取数据的方法
         *
         * @param onClickListener
         */
        String getData( String pass, OnClickListener onClickListener);

        /**
         * 接口
         */
        interface OnClickListener {
            void onComple(int  string);
        }
    }
    public static class OriginalModelPml implements Model {
        @Override
        public String getData( String pass, OnClickListener onClickListener) {

            return pass;
        }
    }

}
