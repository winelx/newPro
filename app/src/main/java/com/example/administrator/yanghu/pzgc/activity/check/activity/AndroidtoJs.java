package com.example.administrator.yanghu.pzgc.activity.check.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/9/17 0017.
 * @description:
 */
public class AndroidtoJs {
    private Activity activity;
    private String str;

    public AndroidtoJs(Activity activity, String str) {
        this.activity = activity;
        this.str = str;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void back(String msg) {
        try {
            activity.finish();
        } catch (Exception e) {
        }


    }



}