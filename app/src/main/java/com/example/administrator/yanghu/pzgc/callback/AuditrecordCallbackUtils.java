package com.example.administrator.yanghu.pzgc.callback;

/**
 * Created by Administrator on 2018/7/18 0018.
 */

public class AuditrecordCallbackUtils {
    private static AuditrecordCallback mCallBack;

    public static void setCallBack(AuditrecordCallback callBack) {
        mCallBack = callBack;
    }

    public static void updata() {
        mCallBack.updata();
    }
}
