package com.example.administrator.newsdf.pzgc.callback;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:
 */

public class ProblemCallbackUtils {
    private static ProblemCallback mCallBack;

    public static void setCallBack(ProblemCallback callBack) {
        mCallBack = callBack;
    }

    public static void deleteProblem(String str) {
        mCallBack.deleteProblem(str);
    }

    public static void addProblem(String str) {
        mCallBack.addProblem(str);
    }
}
