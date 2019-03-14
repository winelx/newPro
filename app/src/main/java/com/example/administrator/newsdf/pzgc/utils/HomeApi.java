package com.example.administrator.newsdf.pzgc.utils;

/**
 * @author lx
 * @data :2019/3/7 0007
 * @描述 : 消息首页接口
 * @see
 */
public class HomeApi {
    /**
     * 消息通知
     */
    public static final String MYSYSTEMNOTICE = Requests.networks + "iface/mobile/systemnotice/mySystemNotice";
    /**
     * 我的待办
     */
    public static final String MYNOTAST = Requests.networks + "iface/mobile/systemnotice/myNoTast";
    /**
     * 已办事项
     */
    public static final String MYYESTAST = Requests.networks + "iface/mobile/systemnotice/myYesTast";
    /**
     * 首页消息
     **/
    public static final String GETMSGNOTICEPAGEDATA = Requests.networks + "iface/mobile/systemnotice/getMsgNoticePageData";

    /**
     * 通知单获取 分公司统计 及 标段统计 数据接口
     */
    public static final String GETNOTICECOUNTDATA = Requests.networks + "iface/mobile/systemnotice/getNoticeCountData";
    /**
     * 累计完成任务
     */
    public static final String GETGRANDTASKFINISHBYF = Requests.networks + "/iface/mobile/systemnotice/getGrandTaskFinishByF";
    /**
     * 今日任务完成数
     */
    public static final String GETTODAYTASKFINISHBYF = Requests.networks + "/iface/mobile/systemnotice/getTodayTaskFinishByF";
    /**
     * 累计完成任务（打开标段页面或 二级页面）数据请求接口
     */
    public static final String GETGRANDTASKFINISHBYB = Requests.networks + "iface/mobile/systemnotice/getGrandTaskFinishByB";
}