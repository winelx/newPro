package com.example.administrator.yanghu.pzgc.callback;

/**
 * @author lx
 * @Created by: 2018/12/7 0007.
 * @description:
 * @Activity：
 */

public class DeviceMeCallbackUtils {
    private static CallBack mCallBack;
    public static void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public static void uopdata(){
        mCallBack.deleteTop();
    }
}
