package com.example.zcjlmodule.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/24 0024.
 */

public class FragmentmineUtils {
    public  interface OnClickLister {
        void onClickLister(int status);
    }

    public void logout(final OnClickLister onClickListener) {
        OkGo.get(Api.LOGOUT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                onClickListener.onClickLister(ret);
                            } else {
                                ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
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
}
