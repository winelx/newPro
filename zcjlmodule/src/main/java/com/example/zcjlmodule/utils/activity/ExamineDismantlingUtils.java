package com.example.zcjlmodule.utils.activity;

import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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
 * @description:
 */

public class ExamineDismantlingUtils {
    public void getData(String standardId, int page, final onclick onclick) {
        OkGo.get(Api.GETLEVYSTANDARDDETAILSBYSTANDARD)
                .params("standardId", standardId)
                .params("page", page)
                .params("size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Map<String, Object> data = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {

                            } else {
                                ToastUtlis.getInstance().showShortToast("msg");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        onclick.onError();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        onclick.onError();
                    }
                });
    }

    public interface onclick {
        void onSuccess(Map<String, Object> map);

        void onError();

    }
}
