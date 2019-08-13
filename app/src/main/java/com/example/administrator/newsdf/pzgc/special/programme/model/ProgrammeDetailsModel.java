package com.example.administrator.newsdf.pzgc.special.programme.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.baselibrary.base.BaseViewModel;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class ProgrammeDetailsModel extends BaseViewModel {
    private MutableLiveData<ProDetails> data;

    public MutableLiveData<ProDetails> getData(String id, String taskid) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        request(id, taskid);
        return data;
    }

    public void request(String id, String taskid) {
        Map<String, String> map = new HashMap<>();
        map.put("specialitemprojectId", id);
        map.put("taskId ", taskid);
        NetWork.postHttp(Api.SPECIALITEMPROJECTDETAIL, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject mdata = jsonObject.getJSONObject("data");
                    ProDetails bean = com.alibaba.fastjson.JSONObject.parseObject(mdata.toString(), ProDetails.class);
                    data.setValue(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }
}
