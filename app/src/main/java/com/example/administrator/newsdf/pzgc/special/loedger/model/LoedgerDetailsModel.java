package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailRecord;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailsOption;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;

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
        NetWork.getHttp(Api.SPECIALITEMMAINDELLIST, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
        list.add(new DetailsOption(""));
        list.add(new DetailsOption(""));
        list.add("处理记录");
        list.add(new DetailRecord(""));
        list.add(new DetailRecord(""));
        list.add(new DetailRecord(""));
        data.setValue(list);
    }
}
