package com.example.administrator.newsdf.service;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class OgranCallbackUtils {
    private static OgranCallback mCallBack;

    public static void setCallBack(OgranCallback callBack) {
        mCallBack = callBack;
    }

    public static void removeCallBackMethod() {
        mCallBack.taskCallback();
    }
}
