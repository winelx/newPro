package com.example.administrator.yanghu.pzgc.callback;


/**
 * description: 任务详情界面任务状态改变，刷新任务列表界面（AllListmessageActivity，MineListmessageActivity）
 * @author lx
 * date: 2018/6/11 0011 上午 9:33
 * update: 2018/6/11 0011
 * version:
*/
public class TaskCallbackUtils {
    private static TaskCallback mTaskCallback;
    public static void setCallBack(TaskCallback callBack) {
        mTaskCallback = callBack;
    }
    public static void CallBackMethod(){
        mTaskCallback.taskCallback();
    }
}
