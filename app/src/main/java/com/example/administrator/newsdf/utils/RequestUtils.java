package com.example.administrator.newsdf.utils;

import com.example.administrator.newsdf.camera.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/15 0015.
 * 网络请求
 */

public class RequestUtils {
    int result = -1;
    public int collection(String wbsId) {
        //添加收藏
        OkGo.post(Requests.WBSSAVE)
                .params("wbsId",wbsId )
                .params("type", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            ToastUtils.showLongToast(jsonObject.getString("msg"));
                            if (ret == 0) {
                                result = 0;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return result;
    }
}
