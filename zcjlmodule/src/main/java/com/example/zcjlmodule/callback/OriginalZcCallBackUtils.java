package com.example.zcjlmodule.callback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @Created by: 2018/10/31 0031.
 * @description:
 */

public class OriginalZcCallBackUtils {
    private static OriginalZcCallBack mCallBack;

    public static void setCallBack(OriginalZcCallBack callBack) {
        mCallBack = callBack;
    }

    public static void updata() {
        mCallBack.updata();
    }
}
