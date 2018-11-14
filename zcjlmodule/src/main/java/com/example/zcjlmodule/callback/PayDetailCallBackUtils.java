package com.example.zcjlmodule.callback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/10/31 0031.
 * @description:
 */

public class PayDetailCallBackUtils {
    private static Callback mCallBack;

    public static void setCallBack(Callback callBack) {
        mCallBack = callBack;
    }

    public static void CallBack(String id, String name,String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("orgId", id);
        map.put("orgname", name);
        map.put("type", type);
        mCallBack.callback(map);
    }
}
