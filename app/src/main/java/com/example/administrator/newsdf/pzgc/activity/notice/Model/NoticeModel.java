package com.example.administrator.newsdf.pzgc.activity.notice.Model;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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


public class NoticeModel extends ViewModel {
    private MutableLiveData<List<Proclamation>> mData;

    private List<Proclamation> lists;

    public MutableLiveData<List<Proclamation>> getmData(Map<String, String> map) {
        if (mData == null) {
            mData = new MutableLiveData<>();
        }
        if (lists == null) {
            lists = new ArrayList<>();
        }
        if (map != null) {
            int page = Integer.parseInt(map.get("nowPage"));
            if (page == 1) {
                lists.clear();
            }
            getdata(map);
        }
        return mData;
    }

    public void getdata(Map<String, String> map) {
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
                        lists.addAll(list);
                        if (lists != null) {
                            mData.setValue(lists);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String code) {

            }
        });
    }

    public interface CallBack {
        void onsuccess(List<Proclamation> data);

        void onerror(String str);
    }
}
