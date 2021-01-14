package com.example.administrator.fengji.pzgc.callback;

/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class BrightCallBackUtils2 {
    private static BrightCallBack mCallBack;

    public static void setCallBack(BrightCallBack callBack) {
        mCallBack = callBack;
    }

    public static void CallBackMethod() {
        mCallBack.bright();
    }

}
