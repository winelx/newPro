package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.LoedgerRecordDetailBean;
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

public class LoedgerRecordDetailModel extends BaseViewModel {
    private MutableLiveData<List<LoedgerRecordDetailBean>> data;
    private List<LoedgerRecordDetailBean> list;

    public MutableLiveData<List<LoedgerRecordDetailBean>> getData(String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        request(id);
        return data;
    }

    private void request(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("specialItemMainDelId", id);
        NetWork.postHttp(Api.SPECIALITEMMAINDEL, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONObject datas = jsonObject.getJSONObject("data");
                        LoedgerRecordDetailBean bean = com.alibaba.fastjson.JSONObject.parseObject(datas.toString(), LoedgerRecordDetailBean.class);
                        list.add(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data.setValue(list);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                modelinface.onerror();
            }
        });

    }
}
