package com.example.zcjlmodule.model;

import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:支付清册
 */

public class DetailedlistModel {
    public interface DetailedlistBaseView extends BaseView {
        String register(String orgId);
    }

    public static class DetailedlistModelIpm implements DetailedlistModel.DetailedlistBaseView {
        @Override
        public String register(String orgId) {

            return "没有数据";
        }

    }
}
