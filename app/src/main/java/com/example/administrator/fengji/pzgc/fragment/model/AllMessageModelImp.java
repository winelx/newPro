package com.example.administrator.fengji.pzgc.fragment.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.administrator.fengji.pzgc.bean.Home_item;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 全部消息界面的mode接口实现层，实现网络请求数据 (mvp的Model)
 *
 * @author lx
 *         date: 2018/6/14 0014 上午 10:58
 *         update: 2018/6/14 0014
 *         version:
 */
public class AllMessageModelImp implements AllMessageModel {
    /**
     * 父级数据集合
     */
    private ArrayList<String> parentlist;
    /**
     * 所有数据
     */
    private List<Home_item> mData;
    /**
     * 每层子集的具体数据
     */
    private Map<String, List<Home_item>> hasMap;

    @Override
    public void getData(final OnClickListener onClickListener) {
        OkGo.post(Requests.TaskMain)
                .params("isAll", true)
                .execute(new StringCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            mData = new ArrayList<>();
                            parentlist = new ArrayList<>();
                            hasMap = new HashMap<>();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String content = json.getString("content");
                                    String createTime = json.getString("updateDate");
                                    if (createTime != null && !"".equals(createTime)) {
                                        createTime = createTime.substring(0, 10);
                                    } else {
                                        createTime = "";
                                    }
                                    String id = json.getString("id");
                                    String isfavorite = json.getString("isfavorite");
                                    String orgId = json.getString("orgId");
                                    String orgName = json.getString("orgName");
                                    String parentid = json.getString("parent_id");
                                    String parentname = json.getString("parent_name");
                                    String unfinish = json.getString("unfinish");
                                    //将组织所属公司添加到集合
                                    if (!parentlist.contains(parentname)) {
                                        parentlist.add(parentname);
                                    }
                                    mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, isfavorite, parentname, parentid, false));
                                }
                                //是否有数据
                                if (mData.size() != 0) {
                                    for (String str : parentlist) {
                                        List<Home_item> list = new ArrayList<Home_item>();
                                        for (Home_item item : mData) {
                                            String name = item.getParentname();
                                            if (str.equals(name)) {

                                                list.add(item);
                                                hasMap.put(str, list);
                                            }
                                        }
                                    }
                                    onClickListener.onComple(parentlist, hasMap);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                onClickListener.onError();
                            }
                        } else {
                            ToastUtils.showShortToast("没有更多数据");
                            onClickListener.onError();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast("网络连接失败");
                        onClickListener.onError();

                    }
                });
    }
}
