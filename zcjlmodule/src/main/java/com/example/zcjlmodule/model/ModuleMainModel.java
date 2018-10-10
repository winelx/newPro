package com.example.zcjlmodule.model;

import com.example.zcjlmodule.utils.Api;

import measure.jjxx.com.baselibrary.frame.MvpModel;

/** 
 * description: 进行网络请求的具体操作并返回数据
 * @author lx
 * date: 2018/10/10 0010 下午 1:10 
 * update: 2018/10/10 0010
 * version: 
*/

public class ModuleMainModel extends MvpModel {
    private static final String TAG = "LoginHttp";

    /**
     * 密码登陆
     *
     * @param account
     * @param password
     */
    public String login(String account, String password) {
     return   Api.login(account,password);
    }

}
