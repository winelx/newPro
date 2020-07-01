package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils;


import android.net.Network;

import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class ExternalModel {

    public void getsafetychecklistbyapp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.getHttp(ExternalApi.GETSAFETYCHECKLISTBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }
}
