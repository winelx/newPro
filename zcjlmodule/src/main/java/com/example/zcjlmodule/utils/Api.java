package com.example.zcjlmodule.utils;


/**
 * description:
 *
 * @author lx
 *         date: 2018/10/29 0029 下午 2:49
 *         update: 2018/10/29 0029
 *         version:
 */
public class Api {
    // public static final String =networks+"";
    //马
    // public static final String networks = "http://192.168.20.36:8082";
    //测
   public static final String networks = "http://192.168.20.120:8082/dtjl/";
    //郑
 //  public static final String networks = "http://192.168.20.40/dtjl/";
    /**
     * 基础接口
     */
    //登录
    public static final String LOGIN = networks + "/admin/login";
    //退出
    public static final String LOGOUT = networks + "/iface/mobile/logout";
    //修改密码
    public static final String PASSWORD = networks + "/iface/mobile/user/alterPwd";
    //新版本检查
    public static final String FINDAPPVERSION = networks + "/iface/mobile/appversion/findAppVersion";
    //获取当前用户的所有组织
    public static final String GETUSERORG = networks + "/iface/mobile/user/getUserOrg";
    //切换组织
    public static final String CHANGEUSERORG = networks + "/iface/mobile/user/changeUserOrg";
    /**
     * 支付清册
     */
    //支付清册
    public static final String GETPAYLISTS = networks + "/iface/levy/getPayLists";
    //支付清册核查单
    public static final String GETPAYCHECKS = networks + "/iface/levy/getPayChecks";
    //获取支付清册核查
    public static final String PAYCHECKDETAILSBYLIST = networks + "/iface/levy/getPayCheckDetailsByList";

    /**
     * 征拆标准
     */
    //获取征拆标准
    public static final String GETLEVYSTANDARD = networks + "/iface/levy/getLevyStandards";
    //获取征拆标准分解接口
    public static final String GETLEVYSTANDARDDETAILS = networks + "/iface/levy/getLevyStandardDetails";
    //征拆标准获取征拆明细
    public static final String GETLEVYSTANDARDDETAILSBYSTANDARD = networks + "/iface/levy/getLevyStandardDetailsByStandard";
    //或者征拆图片
    public static final String GETATTACHMENTS = networks + "iface/sys/attachment/getAttachments";

    /**
     *
     */
    //获取原始勘丈表列表数据
    public static final String GETBUSRAWVALUATIONS = networks + "/iface/levy/getBusRawValuations";

}
