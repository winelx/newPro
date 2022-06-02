package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.administrator.newsdf.pzgc.activity.LoginActivity;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.rx.LiveDataBus;

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
        activity.finish();
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void back() {
        activity.finish();
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void finsh() {
        activity.finish();
    }

    @JavascriptInterface
    public void toLogin() {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        finsh();
    }
}