package com.example.administrator.newsdf.service;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class PushCallbackUtils {
    private static PushCallback mCallBack;

    public static void setCallBack(PushCallback callBack) {
        mCallBack = callBack;

    }

    public static void removeCallBackMethod() {
        mCallBack.deleteTop();
    }
}
