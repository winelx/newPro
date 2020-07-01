package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;


import android.Manifest;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/1 0001}
 * 描述：提取一些基础的魔法值，避免判断时的魔法值情况
 * {@link }
 */
public class Enum {
    //从相册选择图片返回
    public static final int IMAGE_PICKER =1011 ;
    //从相机拍照返回
    public static final int IMAGE_CREAMA =101 ;
    public static final int CHECK_TYPE =103 ;
    public static final int PRROJECT_TYPE =104 ;
    /**
     * 权限
     */
    //定位
    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    //文件读取
    public static final String FILEREAD = Manifest.permission.READ_EXTERNAL_STORAGE;
    //文件写入
    public static final String FILEWRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //相机
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String PACKAGES = Manifest.permission.REQUEST_INSTALL_PACKAGES;


}
