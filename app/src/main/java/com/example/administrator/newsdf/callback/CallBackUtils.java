package com.example.administrator.newsdf.callback;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class CallBackUtils {
    private static CallBack mCallBack;
    public static void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public static void removeCallBackMethod(int pos,String str){
        mCallBack.deleteTop(pos, str);
    }

}
