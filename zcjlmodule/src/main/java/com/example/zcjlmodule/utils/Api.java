package com.example.zcjlmodule.utils;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class Api {
   public static final String networks = "http://192.168.20.36:8082";
   //登录
   public static final String LOGIN = networks+"/admin/login";
   //退出
   public static final String LOGOUT = networks+"/iface/mobile/logout";
   //修改密码
   public static final String PASSWORD=networks+"/iface/mobile/user/alterPwd";
   //新版本检查
   public static final String FINDAPPVERSION=networks+"/iface/mobile/appversion/findAppVersion";
   //获取当前用户的所有组织
   public static final String GETUSERORG=networks+"/iface/mobile/user/getUserOrg";
   //切换组织
   public static final String CHANGEUSERORG=networks+"/iface/mobile/user/changeUserOrg";
}
