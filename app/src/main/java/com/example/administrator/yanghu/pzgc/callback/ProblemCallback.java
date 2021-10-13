package com.example.administrator.yanghu.pzgc.callback;

import com.example.administrator.yanghu.pzgc.bean.DetailsBean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:新增/删除问题接口回调
 * @Activity:ProblemItemActivity
 */

public interface ProblemCallback {
    void problemcallback(ArrayList<DetailsBean> mData);

}
