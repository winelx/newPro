package com.example.administrator.newsdf.pzgc.activity.changed;

import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class ChagedUtils {

    /*获取列表数据*/
    public void getcnflist(boolean lean, int status, String orgId, int page, final CallBack callBack) {
        GetRequest str = OkGo.get(Requests.GETCNFLIST);
        //如果status==-1；
        if (status != -1) {
            str.params("status", status);
        }
        //true:全部，false：我的
        str.params("isAll", lean)
                //组织Id
                .params("orgId", orgId)
                //页数
                .params("page", page)
                //每页长度
                .params("size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {

                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray results = data.getJSONArray("results");
                                List<ChagedList> lists = ListJsonUtils.getListByArray(ChagedList.class, results.toString());
                                Map<String, Object> map = new HashMap<>();
                                map.put("list", lists);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }


    public void getNoticeFormMainInfo(String id, final CallBack callBack) {
        OkGo.get(Requests.GETNOTICEFORMMAININFO)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                ChagedNoticeDetails item = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), ChagedNoticeDetails.class);
                                ArrayList<ChagedNoticeDetails> list = new ArrayList<>();
                                list.add(item);
                                map.put("notice", list);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*获取整改通知单主要信息*/

    public interface CallBack {
        void onsuccess(Map<String, Object> map);

        void onerror(String str);
    }
}
