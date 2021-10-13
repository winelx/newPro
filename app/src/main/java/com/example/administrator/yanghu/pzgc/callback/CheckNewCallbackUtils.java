package com.example.administrator.yanghu.pzgc.callback;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class CheckNewCallbackUtils {
    private static CheckNewCallback callback;

    public static void setCallback(CheckNewCallback checkNewCallback) {
        callback = checkNewCallback;
    }

    public static void CallBackMethod() {
        callback.updata();
    }


}
