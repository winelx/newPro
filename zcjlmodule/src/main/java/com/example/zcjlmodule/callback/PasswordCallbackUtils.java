package com.example.zcjlmodule.callback;

/**
 * Created by Administrator on 2018/10/24 0024.
 */

public class PasswordCallbackUtils {
    private static Callback mCallBack;
    public static void setCallBack(Callback callBack) {
        mCallBack = callBack;
    }

    public static void CallBack(){
        mCallBack.callback();
    }
}
