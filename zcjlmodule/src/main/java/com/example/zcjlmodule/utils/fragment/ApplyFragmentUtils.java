package com.example.zcjlmodule.utils.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:@description:fragment代办展示数据界面
 */

public class ApplyFragmentUtils {
    public void Agencyrequest(final ApplyFragmentUtils.OnClickListener onClickListener) {
        OkGo.get("")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ArrayList<String> list = new ArrayList<>();
                                onClickListener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void Havedonerequest(final ApplyFragmentUtils.HavedoneOnClickListener onClickListener) {
        OkGo.get("")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ArrayList<String> list = new ArrayList<>();
                                onClickListener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public interface OnClickListener {
        void onsuccess(ArrayList<String> list);
    }

    public interface HavedoneOnClickListener {
        void onsuccess(ArrayList<String> list);
    }

}
