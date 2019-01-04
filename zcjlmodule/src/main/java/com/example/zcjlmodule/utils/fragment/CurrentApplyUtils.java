package com.example.zcjlmodule.utils.fragment;

import com.example.zcjlmodule.bean.CapitalBean;
import com.example.zcjlmodule.bean.CurrentApplyBean;
import com.example.zcjlmodule.bean.FlowListBean;
import com.example.zcjlmodule.bean.PeriodListBean;
import com.example.zcjlmodule.callback.NewAddCallback;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/26 0026.
 * @description:资金申请单/审批单
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

    /**
     * @内容: 资金申请单
     * @author lx
     * @date: 2018/12/6 0006 下午 2:48
     */
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

    /**
     * @内容: 资金审批单
     * @author lx
     * @date: 2018/12/6 0006 下午 2:53
     */
    public void approvalheadcounts(String approvalId, final OnclickListener onclickListene) {
        OkGo.get(Api.APPLYHEADCOUNTS)
                .params("applyId", approvalId)
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
                            onclickListene.onSuccess(countList, periodList, flowLists);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * @内容: 征地拆迁资金拨付申请单按指挥部汇总数据
     * @author lx
     * @date: 2018/12/24 0024 下午 4:00
     */
    public void ApplyHeadCounts(String approvalId, String headquarterId, final NewAddCallback callback) {
        OkGo.post(Api.APPROVALDETAILSHOW)
                .params("approvalId", approvalId)
                .params("headquarterId", headquarterId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Map<String, String> map = new HashMap<>();
                        callback.callback(map);
                        try {
                            JSONObject jsonObject = new JSONObject(string);
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


}
