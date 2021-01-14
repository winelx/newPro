package com.example.administrator.fengji.pzgc.callback;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class AuditDetailsCallbackUtils {
    private static AuditDetailsCallback mCallBack;

    public static void setCallBack(AuditDetailsCallback callBack) {
        mCallBack = callBack;
    }

    public static void updata() {
        mCallBack.updata();
    }
}
