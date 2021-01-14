package com.example.administrator.fengji.pzgc.activity.check.activity;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * @author lx
 * @Created by: 2018/9/17 0017.
 * @description:
 */
public class AndroidtoJs {
    private Context mContext;
    private String str;

    public AndroidtoJs(Context mContext, String str) {
        this.mContext = mContext;
        this.str = str;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void back(String msg) {
        if ("task".equals(str)) {
            CheckTaskWebActivity activity = (CheckTaskWebActivity) mContext;
            activity.finsh();
        } else {
            CheckRectificationWebActivity activity = (CheckRectificationWebActivity) mContext;
            activity.finsh();
        }

    }
}