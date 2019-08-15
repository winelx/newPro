package com.example.baselibrary.utils;

import android.app.DownloadManager;

public class Api {
    public static final String SIGNATURE = Requests.networks + "iface/mobile/systemnotice/getPersonalSignature";

    //通知公告
    public static final String PUBLICDATALIST = Requests.networks + "iface/mobile/systemnotice/getPublicDataListByAPP";
    //通知公告查看记录
    public static final String GETLOOKRECORDLIST = Requests.networks + "iface/mobile/systemnotice/getLookRecordList";


    /**
     * @Author lx
     * @创建时间 2019/8/6 0006 13:42
     * @说明 专项施工
     **/
    /* ----------- 专项施工方案------------------*/
    /**
     * 施工方案报批 全部列表
     */
    public static final String SPECIALITEMPROJECT = Requests.networks + "iface/mobile/special/getSpecialitemproject";
    /**
     *  施工方案报批 我的列表
     */
    public static final String MYSPECIALITEMPROJECT = Requests.networks + "iface/mobile/special/getMySpecialitemproject";
    /**
     * 详情
     */
    public static final String SPECIALITEMPROJECTDETAIL = Requests.networks + "iface/mobile/special/getSpecialitemprojectDetail";
    /**
     *审核
     */
    public static final String SPECIALITEMPROJECTVERIFICATION = Requests.networks + "iface/mobile/special/specialItemProjectVerification";

    /*   ----------- 专项施工台账------------------*/
    /**
     * 施工方案台帐我的列表
     */
    public static final String MY_SPECIAL_ITEM_MAIN_LIST = Requests.networks + "iface/mobile/special/getMySpecialItemMainList";
    /**
     * 施工方案台帐全部列表
     */
    public static final String SPECIAL_ITEM_MAIN_LIST = Requests.networks + "iface/mobile/special/getSpecialItemMainList";
    /**
     * 通过施工方案Id获取施工方案详情数据
     */
    public static final String SPECIALITEMMAINDELLIST = Requests.networks + "/iface/mobile/special/getspecialItemMainDelList";
    /**
     * 获取专项施工方案 细明 数据详情
     */
    public static final String SPECIALITEMMAINDEL = Requests.networks + "/iface/mobile/special/getspecialItemMainDel";
    /**
     * 审核接口
     *
     */
    public static final String VERIFICATIONPAGE = Requests.networks + "/iface/mobile/special/verificationPage";

}
