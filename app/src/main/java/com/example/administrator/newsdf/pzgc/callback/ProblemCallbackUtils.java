package com.example.administrator.newsdf.pzgc.callback;

import com.example.administrator.newsdf.pzgc.bean.DetailsBean;

import java.util.ArrayList;

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

    public static void ProblemCallback(ArrayList<DetailsBean> mData) {
        mCallBack.problemcallback(mData);
    }


}
