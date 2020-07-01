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
     *
     *说明：获取外业检查列表接口
     *创建时间： 2020/7/1 0001 16:46
     *@author winelx
     */
    public static final String GETSAFETYCHECKLISTBYAPP = Requests.networks + "iface/mobile/safetycheck/getSafetyCheckListByApp";
}
