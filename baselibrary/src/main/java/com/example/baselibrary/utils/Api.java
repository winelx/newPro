package com.example.baselibrary.utils;

public class Api {
    public static final String SIGNATURE = Requests.networks + "iface/mobile/systemnotice/getPersonalSignature";

    //通知公告
    public static final String PUBLICDATALIST = Requests.networks + "iface/mobile/systemnotice/getPublicDataListByAPP";
    //通知公告查看记录
    public static final String GETLOOKRECORDLIST = Requests.networks + "iface/mobile/systemnotice/getLookRecordList";
}
