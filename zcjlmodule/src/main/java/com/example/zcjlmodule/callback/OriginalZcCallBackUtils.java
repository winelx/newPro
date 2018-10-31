package com.example.zcjlmodule.callback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/10/31 0031.
 * @description:
 */

public class OriginalZcCallBackUtils {
    private static Callback mCallBack;

    public static void setCallBack(Callback callBack) {
        mCallBack = callBack;
    }

    public static void CallBack(String id, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("orgId", id);
        map.put("orgname", name);
        mCallBack.callback(map);
    }
}
