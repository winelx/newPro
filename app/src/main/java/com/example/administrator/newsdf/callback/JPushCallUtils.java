package com.example.administrator.newsdf.callback;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class JPushCallUtils {
    private static JPushCallBack mCallBack;
    public static void setCallBack(JPushCallBack callBack) {

        mCallBack = callBack;
    }
    public static void removeCallBackMethod(){
        String info = "这里CallBackUtils即将发送的数据。";
        mCallBack.doSomeThing(info);
    }
}
