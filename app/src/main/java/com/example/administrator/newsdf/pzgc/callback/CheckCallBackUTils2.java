package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by 10942 on 2018/8/27 0027.
 */

public class CheckCallBackUTils2 {
    private static CheckCallback2 detailsCallback;

    public static void setCallBack(CheckCallback2 callBack) {
        detailsCallback = callBack;
    }

    public static void CheckCallback2(String id) {
        detailsCallback.upate(id);
    }
}
