package com.example.zcjlmodule.utils.fragment;

import android.util.Log;

import com.example.zcjlmodule.utils.Api;
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
    public interface OnClickLister {
        void onClickLister(int status);
    }

    //退出登录
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

    public void findversion(final OnClickLister onClickListener) {

        OkGo.get(Api.FINDAPPVERSION)
                .params("type", 2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("version",s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                //下载地址
                                String downloadUrl = jsonObject1.getString("downloadUrl");
                                //二维码下载
                                String qrcodeUrl = jsonObject1.getString("qrcodeUrl");
                                //版本
                                String version = jsonObject1.getString("version");
                                //藐视
                                String description = jsonObject1.getString("description");
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
                        ToastUtlis.getInstance().showShortToast("请求失败");
                    }
                });
    }
}
