package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by 10942 on 2018/8/27 0027.
 */

public class CheckCallBackUTils1 {
    private static CheckCallback detailsCallback;

    public static void setCallBack(CheckCallback callBack) {
        detailsCallback = callBack;
    }

    public static void CheckCallback(String id) {
        detailsCallback.upate(id);
    }
}
