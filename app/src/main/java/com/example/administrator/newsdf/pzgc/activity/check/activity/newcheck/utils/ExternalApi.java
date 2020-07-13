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
     * 说明：获取面板分数
     * 创建时间： 2020/7/3 0003 15:47
     *
     * @author winelx
     */
    public static final String GETSCOREPANE = Requests.networks + "iface/mobile/safetycheck/getScorePane";

    /**
     * 说明：细项保存接口
     * 创建时间： 2020/7/6 0006 14:32
     *
     * @author winelx
     */
    public static final String SAVESAFETYCHECKDELBYAPP = Requests.networks + "iface/mobile/safetycheck/saveSafetyCheckDelByApp";

    /**
     * 说明：外业检查 提交、确认方法
     * 创建时间： 2020/7/6 0006 17:32
     *
     * @author winelx
     */
    public static final String SUBMITDATABYAPP = Requests.networks + "iface/mobile/safetycheck/submitDataByApp";

    /**
     * 说明：外业检查流程记录
     * 创建时间： 2020/7/7 0007 13:41
     *
     * @author winelx
     */
    public static final String GETPROCESSHISCORD = Requests.networks + "iface/mobile/safetycheck/getProcessHisCord";

    /**
     * 说明：
     * 创建时间： 2020/7/10 0010 13:33
     *
     * @author winelx
     */
    public static final String DELETESAFETYCHECKBYAPP = Requests.networks + "iface/mobile/safetycheck/deleteSafetyCheckByApp";
    /**
     *
     *说明：（异步树）获取外业检查细项位置（树）接口
     *创建时间： 2020/7/10 0010 16:56
     *@author winelx
     */
    public static final String GETWBSTREEBYAPP = Requests.networks + "iface/mobile/safetycheck/getWbstreeByAPP";
    /**
     *
     *说明：数据打回功能
     *创建时间： 2020/7/13 0013 9:54
     *@author winelx
     */
    public static final String RETURNSAFETYCHECKBYAPP = Requests.networks + "iface/mobile/safetycheck/returnSafetyCheckByApp";
    /**
     *
     *说明：导入外业检查数据接口（获取外业检查数据）
     *创建时间： 2020/7/13 0013 18:10
     *@author winelx
     */
    public static final String GETCHOOSESAFEDATABYAPP = Requests.networks + "iface/mobile/safetycheck/getChooseSafeDataByApp";

    /**
     *
     *说明：通知单外业数据导入（通过外业检查id获取检查细项）
     *创建时间： 2020/7/13 0013 18:36
     *@author winelx
     *
     */
    public static final String GETCHOOSESAFEDELDATABYAPP=Requests.networks+"iface/mobile/safetycheck/getChooseSafeDelDataByApp";
    /**
     *
     *说明：保存导入的外业检查细项接口
     *创建时间： 2020/7/13 0013 18:30
     *@author winelx
     */
    public static final String SAVENOTECEDELBYSAFEBYAPP = Requests.networks + "iface/mobile/safetycheck/saveNoteceDelBySafeByApp";

}
