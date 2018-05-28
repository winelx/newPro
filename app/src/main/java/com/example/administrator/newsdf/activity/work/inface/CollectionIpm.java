package com.example.administrator.newsdf.activity.work.inface;

import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class CollectionIpm implements CollectionInterface {
    int ret=-1;
    private ArrayList<Home_item> list ;
    @Override
    public int saveCollection(String taskId) {
        OkGo.<String>post(Requests.SAVECOLLECTION)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ret = jsonObject.getInt("ret");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return ret;
    }

    @Override
    public int deleteCollection(String taskId) {
        OkGo.<String>post(Requests.DELETECOLLECTION)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ret = jsonObject.getInt("ret");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return ret;
    }
    //收藏标段
    @Override
    public int saveList(String taskId) {

        return ret;
    }

    @Override
    public int deleteList(String taskId) {
        return 0;
    }

    //收藏列表
    @Override
    public List<Home_item> getList() {

        return list;
}


}
