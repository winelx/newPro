package com.example.administrator.newsdf.activity.home.allmessage;

import android.content.Context;

import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class AllMessageModlImp implements AllMessageModel {
    /**
     * @param context   上下文
     * @param orgID     orgID
     * @param wbsId     wbsID
     * @param page      页数
     * @param dataState 请求数据状态
     * @param content   搜索框内容
     */
    @Override
    public void OKGO(Context context, String orgID, String wbsId, int page, String dataState, String content) {
        OkGo.<String>post(Request.CascadeList)
                .params("orgId", orgID)
                .params("page", page)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("isAll", "true")
                .params("msgStatus", dataState)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }
                });
    }
}
