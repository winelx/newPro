package com.example.zcjlmodule.model;

import android.util.Log;

import com.example.zcjlmodule.bean.UnknitstandardBean;
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
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:获取征拆标准分解接口
 */

public class UnknitstandardModel {
    public interface OnClicklister {
        void onSuccess(ArrayList<UnknitstandardBean> list);

        void onError();
    }

    public interface Model extends BaseView {
        void getdata(String orgid, int page, OnClicklister onClicklister);
    }

    public static class UnknitstandardPresenterIpm implements Model {

        @Override
        public void getdata(String orgid, int page, final OnClicklister onClicklister) {
            final ArrayList<UnknitstandardBean> list = new ArrayList<>();
            Log.i("ss",page+"");
            OkGo.get(Api.GETLEVYSTANDARDDETAILS)
                    .params("orgId", orgid)
                    .params("page", page)
                    .params("size", 20)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    if (s.contains("data")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject json = jsonArray.getJSONObject(i);
                                            String id = json.getString("id");
                                            //单位
                                            String meterUnitName;
                                            try {
                                                meterUnitName = json.getString("meterUnitName");
                                            } catch (Exception e) {
                                                meterUnitName = "";
                                            }
                                            //title
                                            String number;
                                            try {
                                                number = json.getString("number");
                                            } catch (Exception e) {
                                                number = "";
                                            }
                                            //征拆类型
                                            String levyTypeName;
                                            try {
                                                levyTypeName = json.getString("levyTypeName");
                                            } catch (Exception e) {
                                                levyTypeName = "";
                                            }
                                            //省份
                                            String provinceName;
                                            try {
                                                provinceName = json.getString("provinceName");
                                            } catch (Exception e) {
                                                provinceName = "";
                                            }
                                            //城市
                                            String cityName;
                                            try {
                                                cityName = json.getString("cityName");
                                            } catch (Exception e) {
                                                cityName = "";
                                            }
                                            //区域
                                            String countyName;
                                            try {
                                                countyName = json.getString("countyName");
                                            } catch (Exception e) {
                                                countyName = "";
                                            }
                                            //行政区域
                                            String loadpath = provinceName + cityName + countyName;
                                            //单价
                                            String price;
                                            try {
                                                price = json.getString("price");
                                            } catch (Exception e) {
                                                price = "";
                                            }
                                            //补偿类型
                                            String compensateType;
                                            try {
                                                compensateType = json.getString("compensateType");
                                            } catch (Exception e) {
                                                compensateType = "";
                                            }
                                            list.add(new UnknitstandardBean(id,number,levyTypeName,loadpath,price,meterUnitName,compensateType));
                                        }
                                    }
                                } else {
                                    ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                                }
                                onClicklister.onSuccess(list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            onClicklister.onError();
                        }
                    });

        }
    }
}
