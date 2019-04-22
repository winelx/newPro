package com.example.administrator.newsdf.pzgc.activity.home.utils;


import com.example.administrator.newsdf.pzgc.bean.AduioContent;
import com.example.administrator.newsdf.pzgc.bean.MoretasklistBean;
import com.example.administrator.newsdf.pzgc.inter.JsonCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoredetailsUrils {

    public static void getStringTolist(String str, String wbsName, JsonCallback jsonCallback) {
        ArrayList<AduioContent> contents = new ArrayList<>();
        ArrayList<MoretasklistBean> Dats = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (str.contains("data")) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                //返回数据
                JSONObject Data = jsonObject.getJSONObject("data");
                JSONObject jsonArray = Data.getJSONObject("data");

                //创建时间
                String createDate;
                try {
                    createDate = jsonArray.getString("createDate");
                } catch (JSONException e) {
                    e.printStackTrace();
                    createDate = "";
                }
                //推送天数
                String sendedTimeStr;
                try {
                    sendedTimeStr = jsonArray.getString("sendedTimeStr");
                } catch (JSONException e) {
                    e.printStackTrace();
                    sendedTimeStr = "";
                }
                //责任人
                String leaderName;
                try {
                    leaderName = jsonArray.getString("leaderName");
                } catch (JSONException e) {
                    e.printStackTrace();
                    leaderName = "";
                }
                //责任人Id
                String leaderId;
                try {
                    leaderId = jsonArray.getString("leaderId");
                } catch (JSONException e) {
                    e.printStackTrace();
                    leaderId = "";
                }
                map.put("leaderId", leaderId);
                //是否已读
                String isread = "";
                //创建人ID
                String createByUserID = "";
                //标题名称
                String name;
                try {
                    name = jsonArray.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                    name = "";
                }
                //id
                String id;
                try {
                    id = jsonArray.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    id = "";
                }
                //任务内容
                String content;
                try {
                    content = jsonArray.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                    content = "";
                }
                //状态
                String status;
                try {
                    status = jsonArray.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                    status = "";
                }
                map.put("status", status);
                //状态
                String checkStandard;
                try {
                    checkStandard = jsonArray.getString("checkStandard");
                } catch (JSONException e) {
                    e.printStackTrace();
                    checkStandard = "";
                }
                try {
                    JSONArray parts = Data.getJSONArray("parts");
                    for (int i = 0; i < parts.length(); i++) {
                        JSONObject json = parts.getJSONObject(i);
                        String ids = json.getString("id");
                        String partContent;
                        try {
                            partContent = json.getString("partContent");
                        } catch (JSONException e) {
                            partContent = "";
                        }
                        String uploadDate;
                        try {
                            uploadDate = json.getString("updateDate");
                        } catch (JSONException e) {
                            uploadDate = "";
                        }
                        Dats.add(new MoretasklistBean(uploadDate, partContent, ids));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                contents.add(new AduioContent(id, name, status, content, leaderName, leaderId, isread, createByUserID, checkStandard, createDate, wbsName, null, sendedTimeStr, ""));
                map.put("list1", contents);
                map.put("list2", Dats);
                map.put("WbsName", jsonArray.getString("WbsName"));
                jsonCallback.onsuccess(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
