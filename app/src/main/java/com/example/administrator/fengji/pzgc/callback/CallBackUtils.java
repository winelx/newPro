package com.example.administrator.fengji.pzgc.callback;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class CallBackUtils {
    private static CallBack mCallBack;
    public static void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public static void removeCallBackMethod(){
        mCallBack.deleteTop();
    }
}
