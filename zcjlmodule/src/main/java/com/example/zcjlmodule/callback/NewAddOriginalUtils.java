package com.example.zcjlmodule.callback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/11/9 0009.
 * @description:
 */

public class NewAddOriginalUtils {
    private static NewAddCallback mCallBack;

    public static void setCallBack(NewAddCallback callBack) {
        mCallBack = callBack;
    }

    public static void CallBack( Map<String, String> map) {
        mCallBack.callback(map);
    }
}
