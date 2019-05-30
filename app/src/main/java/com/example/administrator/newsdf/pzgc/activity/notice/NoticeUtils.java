package com.example.administrator.newsdf.pzgc.activity.notice;


import com.example.administrator.newsdf.pzgc.bean.Proclamation;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NoticeUtils {

    public void getdata(Map<String, String> map, CallBack callBack) {
        map.put("rows", 10 + "");
        NetWork.getHttp(Api.PUBLICDATALIST, map, new NetWork.networkCallBacks() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        List<Proclamation> list = new ArrayList<>();
                        JSONArray data = jsonObject.getJSONArray("data");
                        list = ListJsonUtils.getListByArray(Proclamation.class, data.toString());
                        if (list != null) {
                            callBack.onsuccess(list);
                        } else {
                            callBack.onerror("暂无数据");
                        }
                    } else {
                        callBack.onerror(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {
                callBack.onerror(code);
            }
        });
    }

    public interface CallBack {
        void onsuccess(List<Proclamation> data);

        void onerror(String str);
    }
}
