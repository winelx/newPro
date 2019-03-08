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
    public static final String GETMSGNOTICEPAGEDATA=Requests.networks+"/iface/mobile/systemnotice/getMsgNoticePageData";
}
