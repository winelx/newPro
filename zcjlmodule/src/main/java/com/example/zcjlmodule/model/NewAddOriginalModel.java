package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/7 0007.
 * @description:
 */

public class NewAddOriginalModel {
    public interface Model extends BaseView {
        /**
         * 获取数据的方法
         *
         * @param map             数据集合
         * @param onClickListener
         */
        void getData(Map<String, Object> map, NewAddOriginalModel.Model.OnClickListener onClickListener);

        /**
         * 接口
         */
        interface OnClickListener {
            void onComple(ArrayList<PayDetailedlistBean> list);

            void onError();
        }
    }

    public static class NewAddOriginalModelIpm implements NewAddOriginalModel.Model {
        @Override
        public void getData(Map<String, Object> map, OnClickListener onClickListener) {
            OkGo.get(Api.SAVERAW)
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .params("", "")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                        }
                    });
        }
    }
}
