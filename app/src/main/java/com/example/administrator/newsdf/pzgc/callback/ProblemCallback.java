package com.example.administrator.newsdf.pzgc.callback;

import com.example.administrator.newsdf.pzgc.bean.NewDeviceBean;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:特种设备新增/删除问题接口回调
 * @Activity:ProblemItemActivity
 */

public interface ProblemCallback {
    void addProblem( NewDeviceBean bean);

    void deleteProblem(int str);

}
