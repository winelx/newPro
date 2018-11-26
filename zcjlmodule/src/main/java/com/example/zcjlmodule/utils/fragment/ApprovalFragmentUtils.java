package com.example.zcjlmodule.utils.fragment;

import android.view.View;

import com.example.zcjlmodule.bean.ApprovalBean;
import com.example.zcjlmodule.bean.CapitalBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:资金审批单
 */

public class ApprovalFragmentUtils {
    public interface OnClickListener {
        void onsuccess(ArrayList<CapitalBean> data);

    }

    /**
     * @param orgId           组织Id
     * @param status          1已审核 0未审核
     * @param onClickListener 返回数据接口
     * @param:资金审批单
     */
    public void approvallists(String orgId, int status, final OnClickListener onClickListener) {
        OkGo.get(Api.APPROVALLISTS)
                .params("orgId", orgId)
                .params("status", status)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<CapitalBean> list = new ArrayList<>();
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
                    }
                });
    }

}

