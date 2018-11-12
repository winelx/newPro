package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.PayCheckZcBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/12 0012.
 * 获取支付清册核查
 */

public class PayCheckModel {
    public interface OnClickListener {
        void onComple(ArrayList<PayCheckZcBean> list);

        void onerror();
    }

    public interface Model extends BaseView {
        void init(String id, OnClickListener clickListener);

    }

    public static class PayCheckModelPml implements Model {
        @Override
        public void init(String Id, final OnClickListener clickListener) {
            ArrayList<PayCheckZcBean> list = new ArrayList<>();
            OkGo.get(Api.PAYCHECKDETAILSBYLIST)
                    .params("payList", Id)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ArrayList<PayCheckZcBean> list = new ArrayList<>();
                            if (s.contains("data")) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject json = data.getJSONObject(i);
                                        //户主
                                        String householder;
                                        try {
                                            householder = json.getString("householder");
                                        } catch (Exception e) {
                                            householder = "";
                                        }
                                        //支付人
                                        String payPerson;
                                        try {
                                            payPerson = json.getString("payPerson");
                                        } catch (Exception e) {
                                            payPerson = "";
                                        }
                                        //number
                                        String householderIdcard;
                                        try {
                                            householderIdcard = json.getString("householderIdcard");
                                        } catch (Exception e) {
                                            householderIdcard = "";
                                        }
                                        //检查金额
                                        String currCheckMoney;
                                        try {
                                            currCheckMoney = json.getString("currCheckMoney");
                                        } catch (Exception e) {
                                            currCheckMoney = "";
                                        }
                                        //应付金额
                                        String shouldPayMoney;
                                        try {
                                            shouldPayMoney = json.getString("shouldPayMoney");
                                        } catch (Exception e) {
                                            shouldPayMoney = "";
                                        }
                                        list.add(new PayCheckZcBean(householder, householderIdcard, payPerson, shouldPayMoney, currCheckMoney));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            clickListener.onComple(list);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            clickListener.onerror();
                        }
                    });
        }

    }
}
