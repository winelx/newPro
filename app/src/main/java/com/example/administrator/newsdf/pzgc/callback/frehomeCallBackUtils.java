package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class frehomeCallBackUtils {
    private static frehomeCallBack detailsCallback;

    public static void setCallBack(frehomeCallBack callBack) {
        detailsCallback = callBack;
    }

    public static void dohomeCallBackMethod() {
        detailsCallback.bright();
    }
}
