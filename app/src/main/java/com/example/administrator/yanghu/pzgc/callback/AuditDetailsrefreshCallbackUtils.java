package com.example.administrator.yanghu.pzgc.callback;

/**
 * Created by Administrator on 2018/7/18 0018.
 */

public class AuditDetailsrefreshCallbackUtils {
    private static AuditDetailsrefreshCallback callback;

    public static void setCallBack(AuditDetailsrefreshCallback callBack) {
        callback = callBack;
    }

    public static void refreshs() {
        callback.refreshs();
    }
}
