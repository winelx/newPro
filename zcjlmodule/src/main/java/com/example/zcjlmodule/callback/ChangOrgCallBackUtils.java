package com.example.zcjlmodule.callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/25 0025.
 */

public class ChangOrgCallBackUtils {
    private static Callback mCallBack;
    public static void setCallBack(Callback callBack) {
        mCallBack = callBack;
    }

    public static void CallBack(){
        Map<String, Object> map = new HashMap<>();
        mCallBack.callback(map);
    }
}