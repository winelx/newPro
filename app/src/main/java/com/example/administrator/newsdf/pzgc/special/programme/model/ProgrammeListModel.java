package com.example.administrator.newsdf.pzgc.special.programme.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.baselibrary.base.BaseViewModel;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;

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

    private MutableLiveData<List<String>> data;
    private List<String> list;


    /**
     * @Author lx
     * @创建时间 2019/8/6 0006 13:47
     * @说明 查看全局
     **/

    public MutableLiveData<List<String>> getSpecialitemproject(String orgId, int page) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        specialitemproject();
        return data;
    }

    private void mySpecialitemproject() {
        Map<String, String> map = new HashMap<>();
        NetWork.postHttp(Api.Specialitemproject, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
        data.setValue(list);
    }


    /**
     * @Author lx
     * @创建时间 2019/8/6 0006 13:47
     * @说明 查看我的
     **/

    public MutableLiveData<List<String>> getMySpecialitemproject(String orgId, int  page, String isDeal) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        mySpecialitemproject();
        return data;
    }


    public void specialitemproject() {

    }
}
