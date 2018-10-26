package com.example.zcjlmodule.model;

import android.util.Log;

import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.BaseDialog;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class ModuleMainBaseViewIpm {

    public interface Model extends BaseView {
        /**
         * 获取数据的方法
         *
         * @param onClickListener
         */
        String getData(String name, String pass, OnClickListener onClickListener);

        /**
         * 接口
         */
        interface OnClickListener {
            void onComple(int  string);
        }
    }

    public static class ModuleMainBaseViewIPml implements Model {
        @Override
        public String getData(String user, String pass, OnClickListener onClickListener) {
            okgo(user, pass, onClickListener);
            return user;
        }
    }

    public static void okgo(final String user, final String pass, final Model.OnClickListener onClickListener) {
        HttpUrl httpUrl = HttpUrl.parse(Api.networks);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeCookie(httpUrl);
        OkGo.post(Api.networks)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        login(user, pass, onClickListener);
                    }
                    //这个错误是网络级错误，不是请求失败的错误
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtlis.getInstance().showLongToast("网络无法连接到internet");
                    }
                });
    }
    public static void login(String user, String pass, final Model.OnClickListener onClickListener) {
        OkGo.post(Api.LOGIN)
                .params("username", user)
                .params("password", pass)
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("sss",s);
                        try {
                            JSONObject jsonObject =new JSONObject(s);
                            int ret=jsonObject.getInt("ret");
                            if (ret==0){
                                onClickListener.onComple(ret);
                            }else {
                                BaseDialog.dialog.dismiss();
                                ToastUtlis.getInstance().showShortToast("登录失败");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        BaseDialog.dialog.dismiss();
                    }
                });
    }
}
