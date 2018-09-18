package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.administrator.newsdf.camera.ToastUtils;

/**
 * @author lx
 * @Created by: 2018/9/17 0017.
 * @description:
 */
public class AndroidtoJs {
    private Context mContext;

    public AndroidtoJs(Context mContext) {
        this.mContext = mContext;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void back(String msg) {
        CheckWebActivity activity = (CheckWebActivity) mContext;
        activity.finsh();
    }
}