package com.example.administrator.yanghu.pzgc.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.yanghu.pzgc.activity.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class AndroidtoJs {
    private Activity activity;

    public AndroidtoJs(Activity activity) {
        this.activity = activity;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void back() {
        activity.finish();
    }

    //拨打电话
    @JavascriptInterface
    public void call(String str) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    //登录失效
    @JavascriptInterface
    public void toLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    //跳转Activity,并传递参数
    @JavascriptInterface
    public void startActivity(String content, String className) {
        try {
            Class c = Class.forName(className);
            Intent intent = new Intent(activity, c);
            if (content != null && !TextUtils.isEmpty(content)) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                Map<String, String> map = jsonLoop(jsonObject);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
            }
            activity.startActivity(intent);
        } catch (Exception e) {

        }

    }

    /**
     * 说明：解码跳转2.0时后台给参数，
     * 创建时间： 2022/4/13 0013 15:10
     *
     * @author winelx
     */
    public Map<String, String> jsonLoop(com.alibaba.fastjson.JSONObject object) {
        Map<String, String> map = new HashMap<>();
        if (object != null) {
            if (object instanceof com.alibaba.fastjson.JSONObject) {
                com.alibaba.fastjson.JSONObject jsonObject = object;
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    Object o = entry.getValue();
                    map.put(entry.getKey(), entry.getValue() + "");
                }
            }
        }
        return map;
    }

}
