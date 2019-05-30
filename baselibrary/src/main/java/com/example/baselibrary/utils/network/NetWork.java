package com.example.baselibrary.utils.network;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @data :2019/4/22 0022
 * @描述 : 网络请求类
 * @see
 */
public class NetWork {

    /**
     * get请求
     */
    public static void getHttp(String url, Map<String, String> map, final networkCallBack callBack) {
        GetRequest get = OkGo.get(url);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                get.params(entry.getKey(), (String) entry.getValue());
            }
        }
        get.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                callBack.onSuccess(s, call, response);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBack.onError(call, response, e);
            }
        });
    }

    /**
     * get请求
     */
    public static void getHttp(String url, Map<String, String> map, final networkCallBacks callBack) {
        GetRequest get = OkGo.get(url);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                get.params(entry.getKey(), (String) entry.getValue());
            }
        }
        get.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    callBack.onSuccess(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBack.onError(response.code() + "");
            }
        });
    }

    /**
     * post请请你
     *
     * @param map   参数
     * @param form  是否表单提交
     * @param files 传递文件
     */
    public static void postHttp(String url, Map<String, String> map, ArrayList<File> files, boolean form, final networkCallBack callBack) {
        PostRequest post = OkGo.post(url);
        //如果为true，进行表单提交
        if (form) {
            post.isMultipart(form);
        }
        //是否传递文件
        if (files != null) {
            post.addFileParams("imagelist", files);
        }
        //传递参数
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                post.params(entry.getKey(), entry.getValue());

            }
        }
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                callBack.onSuccess(s, call, response);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBack.onError(call, response, e);
            }
        });
    }

    public interface networkCallBack {
        void onSuccess(String s, Call call, Response response);

        void onError(Call call, Response response, Exception e);
    }

    public interface networkCallBacks {
        void onSuccess(String s) throws JSONException;

        void onError(String code);
    }
}
