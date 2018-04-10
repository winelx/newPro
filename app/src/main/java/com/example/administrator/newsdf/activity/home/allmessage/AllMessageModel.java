package com.example.administrator.newsdf.activity.home.allmessage;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public interface AllMessageModel {

    void OKGO(Context context, String orgID, String wbsId, int page, String dataState, String content);
    interface  onLoadCompleteListener{
        void onLoadComplete(List<String> list);
    }

}
