package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class CategoryCallbackUtils {
    private static CategoryCallback mCallBack;

    public static void setCallBack(CategoryCallback callBack) {
        mCallBack = callBack;
    }

    public static void CallBackMethod(String str) {
        mCallBack.updata(str);
    }
}
