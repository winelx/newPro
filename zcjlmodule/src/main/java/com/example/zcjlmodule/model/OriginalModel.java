package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public class OriginalModel {


    public interface OnClicklister {
        void onSuccess(ArrayList<OriginalZcBean> list);

        void onError();
    }

    public interface Model extends BaseView {
        void getdata(String orgid, int page, OriginalModel.OnClicklister onClicklister);
    }


    public static class OriginalModelPml implements Model {
        @Override
        public void getdata(String orgId, int page, final OnClicklister listener) {
            final ArrayList<OriginalZcBean> list = new ArrayList<>();
            OkGo.get(Api.GETBUSRAWVALUATIONS)
                    .params("orgId", orgId)
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
                                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                                        list.addAll(ListJsonUtils.getListByArray(OriginalZcBean.class, s));
//                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                        for (int i = 0; i < jsonArray.length(); i++) {
//                                            JSONObject json = jsonArray.getJSONObject(i);
//                                            try {
//                                                String id = json.getString("id");
//                                                //申报期数
//                                                String periodName = json.getString("periodName");
//                                                //创建人
//                                                String createName = json.getString("createName");
//                                                //创建时间
//                                                String createData = json.getString("createDate").substring(0, 10);
//                                                //单据编号
//                                                String number = json.getString("number");
//                                                //户主名字
//                                                String householder = json.getString("householder");
//                                                //征拆类型
//                                                String levyTypeName = json.getString("levyTypeName");
//                                                //String levyTypeNameid = json.getString("levyTypeName");
//                                                //指挥部
//                                                String headquarterName = json.getString("headquarterName");
//                                           //     String headquarterNameid = json.getString("headquarterName");
//                                                //申报金额 totalPrice
//                                                String totalPrice = json.getString("totalPrice");
//                                                //原始单号
//                                               String rawNumber = json.getString("rawNumber");
////                                                //所属项目
//                                                String attachproject;
//                                                try {
//                                                    attachproject = json.getString("attachproject");
//                                                }catch (Exception e){
//                                                    attachproject="";
//                                                }
//
//                                                //所属标段
//
//                                                //标准分解
//                                                String standardDetailNumber=json.getString("standardDetailNumber");
//                                                //省份
//                                                String provinceName = json.getString("provinceName");
//                                                //城市
//                                                String cityName = json.getString("cityName");
//                                                //区县
//                                                String countyName = json.getString("countyName");
//                                                //乡镇
//                                                String townName = json.getString("townName");
//                                                //申报单位(元，M)
//                                                String meterUnitName = json.getString("meterUnitName");
//                                                //单价
//                                                String price = json.getString("price");
//                                                //申报数量 declareNum
//                                                String declareNum = json.getString("declareNum");
//                                                //身份证
//                                                String householderIdcard = json.getString("householderIdcard");
//                                                //电话
//
//                                                //受益人
//
//                                                //地址
//                                                String detailAddress = json.getString("detailAddress");
//                                                //备注
//                                                String remarks = json.getString("remarks");
//                                                list.add(new OriginalZcBean(id, number, headquarterName, periodName, householder,
//                                                        levyTypeName, createName, createData, totalPrice,provinceName,cityName,
//                                                        countyName,townName,detailAddress,meterUnitName,price,householderIdcard,
//                                                        declareNum,standardDetailNumber,rawNumber,remarks,attachproject));
//                                            } catch (Exception e) {
//                                            }
                                        //     }
                                    }
                                } else {
                                    ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                                }
                                listener.onSuccess(list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {

                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            listener.onError();
                        }
                    });


        }
    }

}
