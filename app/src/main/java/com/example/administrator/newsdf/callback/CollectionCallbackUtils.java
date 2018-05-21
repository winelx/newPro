package com.example.administrator.newsdf.callback;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class CollectionCallbackUtils {
    private static CollectionCallback mTaskCallback;

    public static void setCallBack(CollectionCallback callBack) {
        mTaskCallback = callBack;
    }
    public static void removeCallBackMethod() {
        mTaskCallback.deleteTop();
    }
}
