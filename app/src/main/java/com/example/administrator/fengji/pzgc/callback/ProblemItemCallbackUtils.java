package com.example.administrator.fengji.pzgc.callback;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:
 * @Activity：
 */

public class ProblemItemCallbackUtils {
    private static CheckCallback3 detailsCallback;

    public static void setCallBack(CheckCallback3 callBack) {
        detailsCallback = callBack;
    }

    public static void CheckCallback3(String id) {
        detailsCallback.update(id);
    }
}
