package com.example.administrator.yanghu.pzgc.activity.check;

import com.example.administrator.yanghu.pzgc.adapter.NotSubmitTaskAdapter;
import com.example.administrator.yanghu.pzgc.adapter.SCheckTasklistBean;
import com.example.administrator.yanghu.pzgc.bean.CheckTasklistBean;
import com.example.administrator.yanghu.pzgc.utils.Enums;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author lx
 * @data :2018/8/13 0013.
 * @描述 :
 * @see
 */

public class Checkjson {

    /**
     * description:检查管理列表
     *
     * @author lx
     * date: 2018/8/16 0016 下午 1:50
     * update: 2018/8/16 0016
     * version:
     */
    public void taskmanagerlist(String str, ArrayList<Object> list, NotSubmitTaskAdapter mAdapter) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    try {

                        String checkOrgName;
                        try {
                            checkOrgName = json.getString("checkOrgName");
                        } catch (Exception e) {
                            checkOrgName = "";
                        }

                        String checkUser;
                        try {
                            checkUser = json.getString("checkUser");
                        } catch (Exception e) {
                            checkUser = "";
                        }
                        String createDate;
                        try {
                            createDate = json.getString("createDate");
                            createDate = createDate.substring(0, 10);
                        } catch (Exception e) {
                            createDate = "";
                        }
                        String id = json.getString("id");
                        String orgName;
                        try {
                            orgName = json.getString("wbsMainName");
                        } catch (JSONException e) {
                            orgName = "";
                        }
                        String score;
                        try {
                            score = json.getString("score");
                            BigDecimal bigDecimal = new BigDecimal(score);
                            if (bigDecimal.compareTo(new BigDecimal("0.0")) == 0) {
                                score = "0";
                            }
                        } catch (JSONException e) {
                            score = "";
                        }
                        String status = json.getString("status");
                        String wbsMai;
                        try {
                            wbsMai = json.getString("name");
                        } catch (JSONException e) {
                            wbsMai = "";
                        }
                        int iwork;
                        try {
                            iwork = json.getInt("iwork");
                        } catch (JSONException e) {
                            iwork = 1;
                        }

                        if ("0".equals(status)) {
                            list.add(new SCheckTasklistBean(checkOrgName, checkUser, createDate, id, orgName, status, iwork, wbsMai));
                        }  else {
                            list.add(new CheckTasklistBean(checkOrgName, checkUser, createDate, id, orgName, score, status, iwork, wbsMai));
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        ToastUtils.showShortToast(Enums.ANALYSIS_ERROR);
                    }
                }
            }
            if (list.size() > 0) {
                mAdapter.getData(list);
            } else {
                list.add("str");
                mAdapter.getData(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
