package com.example.administrator.newsdf.callback;

/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class BrightCallBackUtils {
    private static BrightCallBack mCallBack;

    public static void setCallBack(BrightCallBack callBack) {
        mCallBack = callBack;
    }

    public static void removeCallBackMethod() {
        mCallBack.bright();
    }

}
