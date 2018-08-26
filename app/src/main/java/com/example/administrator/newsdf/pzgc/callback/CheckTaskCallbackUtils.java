package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by 10942 on 2018/8/26 0026.
 */

public class CheckTaskCallbackUtils {
    private static CheckTaskCallback detailsCallback;

    public static void setCallBack(CheckTaskCallback callBack) {
        detailsCallback = callBack;
    }

    public static void CallBackMethod() {
        detailsCallback.updata();
    }
}
