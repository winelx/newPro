package com.example.zcjlmodule.callback;

/**
 * Created by Administrator on 2018/10/24 0024.
 */

public class PasswordCallbackUtils {
    private static PasswordCallback mCallBack;
    public static void setCallBack(PasswordCallback callBack) {
        mCallBack = callBack;
    }

    public static void CallBack(){
        mCallBack.callback();
    }
}
