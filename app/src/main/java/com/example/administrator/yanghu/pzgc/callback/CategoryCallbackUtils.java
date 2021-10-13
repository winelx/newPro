package com.example.administrator.yanghu.pzgc.callback;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class CategoryCallbackUtils {
    private static CategoryCallback mCallBack;

    public static void setCallBack(CategoryCallback callBack) {
        mCallBack = callBack;
    }

    public static void CallBackMethod(String str,String str1) {
        mCallBack.updata(str,str1);
    }
}
