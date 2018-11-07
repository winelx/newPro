package com.example.zcjlmodule.model;

import android.util.Log;

import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:支付清册
 */

public class DetailedlistModel {


    public static class DetailedlistModelIpm implements DetailedlistModel.Model {
        @Override
        public void getData(String orgid, int page, final OnClickListener onClickListener) {
            OkGo.get(Api.GETPAYLISTS)
                    .params("orgId", orgid)
                    .params("page", page)
                    .params("size", 20)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            List<PayDetailedlistBean> list = new ArrayList<>();
                            if (s.contains("data")) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    list = ListJsonUtils.getListByArray(PayDetailedlistBean.class, s);
//                                    if (jsonArray.length() > 0) {
//                                        for (int i = 0; i < jsonArray.length(); i++) {
//                                            JSONObject json = jsonArray.getJSONObject(i);
//                                            //期数
//                                            String periodName = json.getString("periodName");
//                                            //内容
//                                            String headquarterName = json.getString("headquarterName");
//                                            //id
//                                            String id = json.getString("id");
//                                            //支付金额
//                                            String totalMoney = json.getString("totalMoney");
//                                            //未检查
//                                            String uncheckMoney = json.getString("uncheckMoney");
//                                            //已检查
//                                            String checkMoney = json.getString("checkMoney");
//                                            //fileNumber编号
//                                            String fileNumber = json.getString("fileNumber");
//                                            //number==title
//                                            String number = json.getString("number");
//                                            list.add(new PayDetailedlistBean(id, number, fileNumber, periodName, headquarterName, totalMoney, checkMoney, uncheckMoney));
//                                        }
//                                    }
                                    onClickListener.onComple(list);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                onClickListener.onComple(list);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            ToastUtlis.getInstance().showShortToast("请求失败");
                            onClickListener.onError();
                        }
                    });
        }
    }

    public interface Model extends BaseView {
        /**
         * 获取数据的方法
         *
         * @param onClickListener
         * @param orgid           组织Id
         */
        void getData(String orgid, int page, OnClickListener onClickListener);

        /**
         * 接口
         */
        interface OnClickListener {
            void onComple(List<PayDetailedlistBean> list);

            void onError();
        }
    }
}
