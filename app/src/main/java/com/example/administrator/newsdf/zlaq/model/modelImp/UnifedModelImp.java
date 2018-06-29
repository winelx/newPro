package com.example.administrator.newsdf.zlaq.model.modelImp;

import com.example.administrator.newsdf.zlaq.model.UnifedModel;
import com.example.administrator.newsdf.zlaq.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 具体操作类
 *
 * @author lx
 *         date: 2018/6/29 0029 下午 4:12
 *         update: 2018/6/29 0029
 *         version:
 */
public class UnifedModelImp implements UnifedModel {


    @Override
    public void login(final String username, final String passwored, final OnClickListener clicklistener) {
        OkGo.<String>post(Api.NETWORK)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        OkGo.<String>post(Api.LOGIN)
                                .params("username", username)
                                .params("password", passwored)
                                .params("mobileLogin", true)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(s);
                                            int ret = jsonObject.getInt("ret");
                                            if (ret == 0) {
                                                clicklistener.onComple();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
    }
}
