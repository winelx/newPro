package com.example.administrator.yanghu.pzgc.callback;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class CheckCallBackUTils3 {
    private static CheckCallback3 detailsCallback;

    public static void setCallBack(CheckCallback3 callBack) {
        detailsCallback = callBack;
    }

    public static void CheckCallback3(String id) {
        detailsCallback.update(id);
    }
}
