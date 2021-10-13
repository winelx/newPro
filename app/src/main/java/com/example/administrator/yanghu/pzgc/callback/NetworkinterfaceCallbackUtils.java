package com.example.administrator.yanghu.pzgc.callback;

import java.util.HashMap;
import java.util.Map;

public class NetworkinterfaceCallbackUtils {
    private static Networkinterface mCallBack;

    public static void setCallBack(Networkinterface callBack) {
        mCallBack = callBack;
    }

    public static void Refresh(String string) {
        Map<String, Object> map = new HashMap<>();
        map.put(string, string);
        mCallBack.onsuccess(map);
    }
}
