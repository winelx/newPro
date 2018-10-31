package com.example.zcjlmodule.utils.activity;

import android.util.Log;

import com.example.zcjlmodule.bean.ExamineBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/10/30 0030.
 * @description: 获取征拆图片
 * activity：ExamineDismantlingActivity
 */

public class ExamineDismantlingUtils {
    private static final String TAG = "utils";
    /**
     * 请求数据
     *
     * @param standardId Id
     * @param onclick    点击事件的即可
     */
    public void getData(String standardId, final onclick onclick) {
        OkGo.get(Api.GETATTACHMENTS)
                .params("billId", standardId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "onSuccess: "+s);
                        ArrayList<ExamineBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (s.contains("data")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String filename;
                                        try {
                                            filename = json.getString("filename");
                                        } catch (Exception e) {
                                            filename = "";
                                        }
                                        String filepath;
                                        try {
                                            filepath = Api.networks + json.getString("filepath");
                                        } catch (Exception e) {
                                            filepath = "";
                                        }
                                        String type;
                                        try {
                                            type = json.getString("type");
                                        } catch (Exception e) {
                                            type = "";
                                        }
                                        list.add(new ExamineBean(filename, filepath, type));
                                    }
                                }
                                onclick.onSuccess(list);
                            } else {
                                ToastUtlis.getInstance().showShortToast("msg");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        onclick.onError();
                    }
                });
    }

    /**
     * 点击事件接口
     */
    public interface onclick {
        /**
         * 成功后的方法
         *
         * @param data 返回的数据
         */
        void onSuccess(ArrayList<ExamineBean> data);

        /**
         * 失败后的方法
         */
        void onError();

    }
}
