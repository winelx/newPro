package com.example.administrator.yanghu.pzgc.callback;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/23 0023.
 */

public class MapCallbackUtils {
    private static MapCallback mCallBack;

    public static void setCallBack(MapCallback callBack) {

        mCallBack = callBack;
    }

    public static void CallBackMethod(Map<String, Object> map) {
        mCallBack.getdata( map);
    }
}
