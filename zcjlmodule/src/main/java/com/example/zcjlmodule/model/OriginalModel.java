package com.example.zcjlmodule.model;

import android.icu.math.BigDecimal;


import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import measure.jjxx.com.baselibrary.utils.LogUtil;
import measure.jjxx.com.baselibrary.utils.TextUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/15 0015.
 * 登录
 */

public class OriginalModel {


    public interface OnClicklister {
        void onSuccess(ArrayList<OriginalZcBean> list, String str);

        void onError();
    }

    public interface Model extends BaseView {
        void getdata(String orgid, int page, Map<String, String> map, OriginalModel.OnClicklister onClicklister);
    }


    public static class OriginalModelPml implements Model {
        String totalmoney = null;

        @Override
        public void getdata(String orgId, final int page, Map<String, String> map, final OnClicklister listener) {
            final ArrayList<OriginalZcBean> list = new ArrayList<>();
            GetRequest getrequest = OkGo.get(Api.GETBUSRAWVALUATIONS)
                    .params("orgId", orgId)
                    .params("page", page)
                    .params("size", 20);
            //如果page ==1，就计算总金额
            if (page == 0) {
                getrequest.params("isCount", true);
            }
            //如果map大于0，就将map传递（map的筛选类型）也坑可能没有值所以进行空异常处理
            try {
                if (map.size() > 0) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        getrequest.params(entry.getKey(), entry.getValue());
                    }
                }
            } catch (NullPointerException e) {
            }
            getrequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int ret = jsonObject.getInt("ret");
                        if (ret == 0) {
                            //请求成功
                            if (s.contains("data")) {
                                //是否返回数据
                                if (page == 0) {
                                    //如果请求第一页，获取总金额
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
                                    totalmoney = jsonObject1.getString("totalMoney");
                                } else {
                                    totalmoney = "";
                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                list.addAll(ListJsonUtils.getListByArray(OriginalZcBean.class, jsonArray.toString()));
                                if (page == 0) {
                                    listener.onSuccess(list, TextUtils.bigdecimal(totalmoney));
                                } else {
                                    listener.onSuccess(list, "");
                                }
                            } else {
                                //没有返回数据
                                listener.onSuccess(list, "");
                            }
                        } else {
                            //请求失败
                            ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                            listener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    ToastUtlis.getInstance().showShortToast("请求失败");
                    listener.onError();
                }
            });
        }
    }
}
