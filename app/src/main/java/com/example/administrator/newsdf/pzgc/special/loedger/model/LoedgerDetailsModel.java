package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailRecord;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailsOption;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class LoedgerDetailsModel extends ViewModel {
    private MutableLiveData<List<Object>> data;
    private List<Object> list;

    public MutableLiveData<List<Object>> getData(String specialItemMainId, String taskId) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        request(specialItemMainId, taskId);
        return data;
    }

    private void request(String specialItemMainId, String taskId) {
        Map<String, String> map = new HashMap<>();
        map.put("specialItemMainId", specialItemMainId);
        map.put("taskId", taskId);
        NetWork.postHttp(Api.SPECIALITEMMAINDELLIST, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    list.clear();
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    JSONObject mData = jsonObject.getJSONObject("data");
                    String permission = mData.getString("permission");
                    permissioncallback.callback(permission);
                    if (ret == 0) {
                        JSONArray data1 = mData.getJSONArray("list");
                        list.addAll(com.alibaba.fastjson.JSONObject.parseArray(data1.toString(), DetailsOption.class));
                        JSONArray data2 = mData.getJSONArray("recordList");
                        if (data2.length() > 0) {
                            list.add("处理记录");
                            list.addAll(com.alibaba.fastjson.JSONObject.parseArray(data2.toString(), DetailRecord.class));
                        }
                        data.setValue(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }

    public interface Permissioncallback {
        void callback(String str);
    }

    private Permissioncallback permissioncallback;

    public void getCallback(Permissioncallback permissioncallback) {
        this.permissioncallback = permissioncallback;
    }
}
