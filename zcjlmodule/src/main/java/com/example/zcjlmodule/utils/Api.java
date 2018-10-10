package com.example.zcjlmodule.utils;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class Api {
    public static String login(String name, String passowrd) {

        if (name.equals("111") && passowrd.equals("111")) {
            return "成功";
        } else {
            return "失败";
        }
    }
}
