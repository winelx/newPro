package com.example.zcjlmodule.utils.fragment;

import com.example.zcjlmodule.bean.CapitalBean;
import com.example.zcjlmodule.bean.CurrentApplyBean;
import com.example.zcjlmodule.bean.FlowListBean;
import com.example.zcjlmodule.bean.PeriodListBean;
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
 * @Created by: 2018/11/26 0026.
 * @description:
 */

public class CurrentApplyUtils {
    public interface OnclickListener {
        /**
         * @param data       本期
         * @param periodList 累计
         * @param flowLists  流程
         */
        void onSuccess(ArrayList<CurrentApplyBean> data, ArrayList<PeriodListBean> periodList, ArrayList<FlowListBean> flowLists);
    }

    public void applyheadcounts(String applyId, final OnclickListener onclickListener) {
        OkGo.get(Api.APPLYHEADCOUNTS)
                .params("applyId", applyId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<CurrentApplyBean> countList = new ArrayList<>();
                        ArrayList<PeriodListBean> periodList = new ArrayList<>();
                        ArrayList<FlowListBean> flowLists = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonArray = jsonObject.getJSONObject("data");
                            //本期
                            JSONArray countLists = jsonArray.getJSONArray("countList");
                            countList.addAll(ListJsonUtils.getListByArray(CurrentApplyBean.class, countLists.toString()));
                            //累计
                            JSONArray periodLists = jsonArray.getJSONArray("periodList");
                            periodList.addAll(ListJsonUtils.getListByArray(PeriodListBean.class, periodLists.toString()));
                            //流程
                            JSONArray flowList = jsonArray.getJSONArray("flowList");
                            flowLists.addAll(ListJsonUtils.getListByArray(FlowListBean.class, flowList.toString()));
                            onclickListener.onSuccess(countList, periodList, flowLists);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
