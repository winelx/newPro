package com.example.administrator.newsdf.pzgc.utils;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/15 0015.
 * @description: 申请权限的接口
 */

public interface PermissionListener {
    void onGranted();//已授权
    void onDenied(List<String> deniedPermission);//未授权
}
