package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.SdDismantlingBean;
import com.example.zcjlmodule.view.SdDismantlingView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class SdDismantlingModel {
    public interface OnClickListener {
        void callBack(ArrayList<SdDismantlingBean> list);
    }

    public interface Model extends BaseView {
        void init(String string, OnClickListener onClickListener);
    }

    public static class SdDismantlingModelIpm implements Model {
        @Override
        public void init(String string, OnClickListener onClickListener) {
            ArrayList<SdDismantlingBean> list = new ArrayList();
            for (int i = 0; i < 10; i++) {
                list.add(new SdDismantlingBean("d", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据"));
            }
            onClickListener.callBack(list);
        }
    }
}
