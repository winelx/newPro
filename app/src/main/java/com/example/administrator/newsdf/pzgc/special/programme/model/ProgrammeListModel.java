package com.example.administrator.newsdf.pzgc.special.programme.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.newsdf.pzgc.special.programme.bean.ProAllListBean;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProMineListBean;
import com.example.baselibrary.base.BaseViewModel;
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


/**
 * @Author lx
 * @创建时间 2019/8/1 0001 14:14
 * @说明
 **/

public class ProgrammeListModel extends BaseViewModel {

    private MutableLiveData<List<Object>> data;
    private List<Object> list;


    /**
     * @Author lx
     * @创建时间 2019/8/6 0006 13:47
     * @说明 查看全局
     **/

    public MutableLiveData<List<Object>> getSpecialitemproject(String orgId, int page, String choice) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (page==1){
            list.clear();
        }
        specialitemproject(orgId, choice, page);
        return data;
    }

    public void specialitemproject(String orgId, String choice, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgId);
        map.put("page", page + "");
        map.put("rows", "15");
        map.put("isDeal", choice);
        NetWork.postHttp(Api.SPECIALITEMPROJECT, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("results");
                    list.addAll(com.alibaba.fastjson.JSONObject.parseArray(array.toString(), ProAllListBean.class));
                    data.setValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });

    }


    /**
     * @Author lx
     * @创建时间 2019/8/6 0006 13:47
     * @说明 查看我的
     **/

    public MutableLiveData<List<Object>> getMySpecialitemproject(String orgId, int page, String choice) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (page==1){
            list.clear();
        }
        mySpecialitemproject(orgId, page, choice);
        return data;
    }

    private void mySpecialitemproject(String orgId, int page, String choice) {
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgId);
        map.put("page", page + "");
        map.put("rows", "15");
        map.put("isDeal", choice);
        NetWork.postHttp(Api.MYSPECIALITEMPROJECT, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("results");
                    list.addAll(com.alibaba.fastjson.JSONObject.parseArray(array.toString(), ProMineListBean.class));
                    data.setValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
        data.setValue(list);
    }

}
