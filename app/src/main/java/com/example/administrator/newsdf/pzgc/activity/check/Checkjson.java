package com.example.administrator.newsdf.pzgc.activity.check;

import com.example.administrator.newsdf.pzgc.Adapter.NotSubmitTaskAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.SCheckTasklistAdapter;
import com.example.administrator.newsdf.pzgc.bean.CheckTasklistAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class Checkjson {

    public ArrayList<CheckTasklistAdapter> checkmangerlist(String str) {
        ArrayList<CheckTasklistAdapter> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                try {
                    String checkOrgName = json.getString("checkOrgName");
                    String checkUser = json.getString("checkUser");
                    String createDate = json.getString("createDate");
                    String id = json.getString("id");
                    String orgName = json.getString("orgName");
                    String score = json.getString("score");
                    String status = json.getString("status");
                    String wbsMai = json.getString("wbsMai");
                    list.add(new CheckTasklistAdapter(checkOrgName, checkUser, createDate, id, orgName, score, status, wbsMai));
                } catch (NullPointerException e) {
                    String checkOrgName = "";
                    String checkUser = "";
                    String createDate = "";
                    String id = "";
                    String orgName = "";
                    String score = "";
                    String status = "";
                    String wbsMai = "";
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * description:检查管理列表
     *
     * @author lx
     * date: 2018/8/16 0016 下午 1:50
     * update: 2018/8/16 0016
     * version:
     */
    public void taskmanagerlist(String str, ArrayList<Object> list, NotSubmitTaskAdapter mAdapter) {
        ArrayList<CheckTasklistAdapter> mdata = new ArrayList<>();
        ArrayList<CheckTasklistAdapter> listsuccess = new ArrayList<>();
        ArrayList<SCheckTasklistAdapter> listsub = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    try {
                        String checkOrgName = json.getString("checkOrgName");
                        String checkUser = json.getString("checkUser");
                        String createDate = json.getString("createDate");
                        createDate = createDate.substring(0, 10);
                        String id = json.getString("id");
                        String orgName;
                        try {
                            orgName = json.getString("orgName");
                        } catch (JSONException e) {
                            orgName = "";
                        }
                        String score;
                        try {
                            score = json.getString("score");
                        } catch (JSONException e) {
                            score = "";
                        }
                        String status = json.getString("status");
                        String wbsMai;
                        try {
                            wbsMai = json.getString("wbsMainName");
                        } catch (JSONException e) {
                            wbsMai = "";
                        }
                        if (status.equals("0")) {
                            listsub.add(new SCheckTasklistAdapter(checkOrgName, checkUser, createDate, id, orgName, status, wbsMai));
                        } else {
                            listsuccess.add(new CheckTasklistAdapter(checkOrgName, checkUser, createDate, id, orgName, score, status, wbsMai));
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                if (listsuccess.size() > 0) {
                    list.addAll(listsuccess);
                }
                if (listsub.size() > 0) {
                    list.addAll(listsub);
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
