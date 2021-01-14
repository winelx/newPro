package com.example.administrator.fengji.pzgc.activity.check.activity.record.utils;

import com.example.baselibrary.utils.Requests;

/**
 * 说明：监督检查接口说明
 * 创建时间： 2020/7/30 0030 10:08
 *
 * @author winelx
 */
public class RecordApi {
    /**
     * 说明：组织树接口
     * 创建时间： 2020/7/30 0030 9:56
     *
     * @author winelx
     */
    public static final String GETORGINFOBYCNFBYRECORD = Requests.networks + "iface/mobile/special/getOrgInfoBycnfByRecord";
    /**
     * 说明：列表
     * 创建时间： 2020/7/30 0030 10:08
     *
     * @author winelx
     */
    public static final String GETSAFETYCHECKLISTBYAPP = Requests.networks + "iface/mobile/special/getSafetyCheckListByApp";
    /**
     * 说明：详情实体
     * 创建时间： 2020/7/30 0030 11:01
     *
     * @author winelx
     */
    public static final String GETSPECIALCHECKRECORDBYAPP = Requests.networks + "iface/mobile/special/getSpecialCheckRecordByApp";


    /**
     * 说明：人员选择公共方法
     * 创建时间： 2020/7/30 0030 16:42
     *
     * @author winelx
     */
    public static final String CHOOSEPERSIONBYAPP = Requests.networks + "iface/mobile/special/choosePersionByApp";

    /**
     * 说明：检查记录删除接口
     * 创建时间： 2020/7/31 0031 13:19
     *
     * @author winelx
     */
    public static final String DELETESPECIALCHECKRECORDBYAPP = Requests.networks + "iface/mobile/special/deleteSpecialCheckRecordByApp";

    /**
     * 说明：保存检查记录接口
     * 创建时间： 2020/7/31 0031 13:22
     *
     * @author winelx
     */
    public static final String SAVESPECIAL = Requests.networks + "iface/mobile/special/saveSpecialCheckRecordByApp";

    /**
     *
     *说明：
     *创建时间： 2020/7/31 0031 14:22
     *@author winelx
     */
    public static final String OPTIONSTATUSBYAPP = Requests.networks + "iface/mobile/special/optionStatusByApp";

}
