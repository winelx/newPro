package com.example.zcjlmodule.model;

import android.graphics.ColorSpace;

import com.example.zcjlmodule.bean.UnknitstandardBean;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class UnknitstandardModel {
    public interface OnClicklister {
        void Callback(ArrayList<UnknitstandardBean> list);
    }

    public interface Model extends BaseView {
        void getdata(String str, OnClicklister onClicklister);
    }

    public static class UnknitstandardPresenterIpm implements Model {

        @Override
        public void getdata(String str, OnClicklister onClicklister) {
            ArrayList<UnknitstandardBean> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new UnknitstandardBean("1", "测速数据", "测试数据", "测速数据", "测速数据", "测速数据", "测速数据"));
            }
            onClicklister.Callback(list);
        }
    }
}
