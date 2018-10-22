package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.PayCheckZcBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class PayCheckModel {
    public interface OnClickListener {
        void onComple(ArrayList<PayCheckZcBean> list);
    }
    public interface Model extends BaseView {
        void init(String user, OnClickListener clickListener);

    }
    public static class PayCheckModelPml implements Model {
        @Override
        public void init(String user, OnClickListener clickListener) {
            ArrayList<PayCheckZcBean> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new PayCheckZcBean("测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据"));
            }
            clickListener.onComple(list);
        }
    }

}
