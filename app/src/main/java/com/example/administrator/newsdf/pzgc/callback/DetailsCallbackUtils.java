package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class DetailsCallbackUtils {
    private static DetailsCallback detailsCallback;
    public static void setCallBack(DetailsCallback callBack) {
        detailsCallback = callBack;
    }
    public static void dohomeCallBackMethod(){
        detailsCallback.deleteTop();
    }
}
