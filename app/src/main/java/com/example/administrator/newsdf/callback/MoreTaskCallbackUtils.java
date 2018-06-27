package com.example.administrator.newsdf.callback;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class MoreTaskCallbackUtils {

    private static MoreTaskCallback mCallBack;

    public static void setCallBack(MoreTaskCallback callBack) {

        mCallBack = callBack;
    }

    public static void removeCallBackMethod() {
        mCallBack.newData();
    }
}
