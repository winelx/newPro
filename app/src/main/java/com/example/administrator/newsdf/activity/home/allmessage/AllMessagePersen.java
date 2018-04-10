package com.example.administrator.newsdf.activity.home.allmessage;

import android.content.Context;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class AllMessagePersen {
    private AllMessageModel messageModel;

    public AllMessagePersen() {
        messageModel = new AllMessageModlImp();
    }

    public void okHttp(Context context, String orgID, String wbsId, int page, String dataState, String content) {
        messageModel.OKGO(context, orgID, wbsId, page, dataState, content);
    }

}
