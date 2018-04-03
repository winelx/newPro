package com.example.administrator.newsdf.service;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class TaskCallbackUtils {
    private static TaskCallback mTaskCallback;

    public static void setCallBack(TaskCallback callBack) {
        mTaskCallback = callBack;
    }
    public static void removeCallBackMethod(){
        mTaskCallback.taskCallback();
    }
}
