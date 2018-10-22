package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.OriginalZcBean;

import java.util.ArrayList;

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
        void getData( String pass, OnClickListener onClickListener);

        /**
         * 接口
         */
        interface OnClickListener {
            void onComple(ArrayList<OriginalZcBean> list);
        }
    }
    public static class OriginalModelPml implements Model {
        @Override
        public void getData( String pass, OnClickListener Listener) {
            ArrayList<OriginalZcBean> list=new ArrayList<>();
            for (int i = 0; i <10 ; i++) {
                list.add(new OriginalZcBean("0", "MSTJ-01-002", "赫章县高速公路铁路建设指挥部",
                        "第 01 期", "户主名字：集体土地 (1245421.5)", "征拆类别：拆迁管理/拆迁管理/拆迁管理/拆迁管理",
                        "张三", "2018-03-01"));
            }
            Listener.onComple(list);
        }
    }

}
