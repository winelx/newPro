package com.example.zcjlmodule.utils.fragment;

import com.example.zcjlmodule.bean.CapitalBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:@description:fragment代办展示数据界面
 */

public class ApplyFragmentUtils {
    public void Agencyrequest(final ApplyFragmentUtils.OnClickListener onClickListener) {
        OkGo.get("")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ArrayList<String> list = new ArrayList<>();
//                                onClickListener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void Havedonerequest(final ApplyFragmentUtils.HavedoneOnClickListener onClickListener) {
        OkGo.get("")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ArrayList<String> list = new ArrayList<>();
                                onClickListener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 资金申请单
     *
     * @param orgId           组织Id
     * @param status          1已审核0未审核
     * @param onClickListener 接口
     */
    public void applylists(String orgId, int status, final ApplyFragmentUtils.OnClickListener onClickListener) {
        final ArrayList<CapitalBean> list = new ArrayList<>();
        OkGo.get(Api.APPLYLISTS)
                .params("orgId", orgId)
                .params("status", status)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            list.addAll(ListJsonUtils.getListByArray(CapitalBean.class, jsonArray.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        onClickListener.onsuccess(list);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        onClickListener.onsuccess(list);
                    }
                });
    }


    public interface OnClickListener {
        void onsuccess(ArrayList<CapitalBean> list);
    }

    public interface HavedoneOnClickListener {
        void onsuccess(ArrayList<String> list);
    }


}
