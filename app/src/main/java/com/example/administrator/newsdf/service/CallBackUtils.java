package com.example.administrator.newsdf.service;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class CallBackUtils {
    private static CallBack mCallBack;
    private static HomeCallback homemCallBack;
    public static void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
    public static void sethomeCallBack(HomeCallback homecallBack) {
        homemCallBack = homecallBack;
    }
    public static void removeCallBackMethod(int pos,String str){
        String info = "这里CallBackUtils即将发送的数据。";
        mCallBack.deleteTop(pos, str);
    }
    public static void dohomeCallBackMethod(){
        homemCallBack.doSomeThing();
    }
}
