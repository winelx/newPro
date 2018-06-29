package com.example.administrator.newsdf.zlaq.model;

/**
 * description: model的接口类-
 *
 * @author lx
 *         date: 2018/6/29 0029 下午 3:58
 *         update: 2018/6/29 0029
 *         version:
 */
public interface UnifedModel {
    //登录
    void login(String username, String passwored, OnClickListener onClickListener);

    /**
     * 接口
     */
    interface OnClickListener {
        void onComple();
    }
}
