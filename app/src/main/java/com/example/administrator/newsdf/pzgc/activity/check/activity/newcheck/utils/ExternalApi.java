package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils;

import com.example.baselibrary.utils.Requests;

public class ExternalApi {
    /**
     * 说明：获取组织数据接口
     * 创建时间： 2020/7/1 0001 13:36
     *
     * @author winelx
     */
    public static final String GETORGINFOBYSAFETYCHECK = Requests.networks + "iface/mobile/safetycheck/getOrgInfoBySafetyCheck?isAll=true";
    /**
     * 说明：获取外业检查列表接口
     * 创建时间： 2020/7/1 0001 16:46
     *
     * @author winelx
     */
    public static final String GETSAFETYCHECKLISTBYAPP = Requests.networks + "iface/mobile/safetycheck/getSafetyCheckListByApp";
    /**
     * 说明：获取外业检查列表接口
     * 创建时间： 2020/7/1 0001 16:46
     *
     * @author winelx
     */
    public static final String GETWBSTASKTYPEBYAPP = Requests.networks + "iface/mobile/safetycheck/getWbstasktypeByApp";

    /**
     * 说明：获取外业实体数据（此接口包含外业实体数据、权限数据、分数面板数据）
     * 创建时间： 2020/7/2 0002 14:41
     *
     * @author winelx
     */
    public static final String GETSAFETYCHECK = Requests.networks + "iface/mobile/safetycheck/getSafetyCheck";
    /**
     * 说明：保存外业检查主数据
     * 创建时间： 2020/7/2 0002 14:41
     *
     * @author winelx
     */
    public static final String SAVESAFETYCHECKBYAPP = Requests.networks + "iface/mobile/safetycheck/saveSafetyCheckByApp";

    /**
     * 说明：获取外业检查细项实体结对
     * 创建时间： 2020/7/3 0003 15:25
     *
     * @author winelx
     */
    public static final String GETSAFETYCHECKDELBYAPP = Requests.networks + "iface/mobile/safetycheck/getSafetyCheckDelByApp";

    /**
     *
     *说明：获取面板分数
     *创建时间： 2020/7/3 0003 15:47
     *@author winelx
     */
    public static final String GETSCOREPANE = Requests.networks + "iface/mobile/safetycheck/getScorePane";

}
