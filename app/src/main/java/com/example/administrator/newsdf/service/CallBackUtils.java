package com.example.administrator.newsdf.service;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class CallBackUtils {
    private static CallBack mCallBack;

    public static void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public static void doCallBackMethod(){
        String info = "这里CallBackUtils即将发送的数据。";
        mCallBack.doSomeThing(info);
    }

}
