package com.example.administrator.newsdf.pzgc.callback;

/**
 * @author lx
 * @Created by: 2018/12/16 0016.
 * @description:
 * @Activity：
 */

public class DeviceDetailsCallBackUtils {
    private static CallBack mCallBack;
    public static void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public static void CallBackMethod(){
        mCallBack.deleteTop();
    }
}
