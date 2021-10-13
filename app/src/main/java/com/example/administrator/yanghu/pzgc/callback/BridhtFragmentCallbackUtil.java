package com.example.administrator.yanghu.pzgc.callback;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class BridhtFragmentCallbackUtil {
    private static BridhtFragmentCallback mCallBack;

    public static void setCallBack(BridhtFragmentCallback callBack) {
        mCallBack = callBack;
    }

    public static void updata() {
        mCallBack.updata();
    }
}
