package com.example.administrator.newsdf.callback;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class HideCallbackUtils {
    private static HideCallback mTaskCallback;

    public static void setCallBack(HideCallback callBack) {
        mTaskCallback = callBack;
    }
    public static void removeCallBackMethod() {
        mTaskCallback.deleteTop();
    }
}
