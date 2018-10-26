package com.example.zcjlmodule.utils.activity;


import com.example.zcjlmodule.treeView.bean.OrgBeans;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.utils.SPUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;
import release.App;

/**
 * description:
 *
 * @author lx
 *         date: 2018/10/25 0025 上午 10:46
 *         update: 2018/10/25 0025
 *         version:
 */
public class UserOrgZcUtils {
    boolean status = true;
    List<OrgBeans> mDatas2 = new ArrayList<>();
    List<OrgenBeans> mData = new ArrayList<>();

    public interface OnClickListener {
        void onClick(List<OrgBeans> data, List<OrgenBeans> data2);
    }

    public interface OnChangeClickListener {
        void onClick(int ret);
    }

    //获取组织
    public void getuserorg(final OnClickListener onClickListener) {
        OkGo.get(Api.GETUSERORG)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            if (jsonArray1.length() > 0) {
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                    JSONObject json = jsonObject1.getJSONObject("organization");
                                    String Id;
                                    try {
                                        Id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Id = "";
                                    }
                                    String type;
                                    try {
                                        type = json.getString("orgType");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        type = "";
                                    }
                                    String parentId;
                                    try {
                                        parentId = json.getString("parentId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        //如果父ID为null
                                        parentId = "";
                                        //当做第一级处理
                                        status = false;
                                        mDatas2.add(new OrgBeans(1, 0, json.getString("name"), Id, parentId, type));
                                    }
                                    String name;
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        name = "";
                                    }
                                    onClickListener.onClick(mDatas2, mData);
                                    mData.add(new OrgenBeans(Id, parentId, name, type));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //切换组织
    public void changorg(String orgId, String orgName, final OnChangeClickListener onClickListener) {
        OkGo.get(Api.CHANGEUSERORG)
                .params("orgId", orgId)
                .params("orgName", orgName)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                SPUtils.putString(App.getInstance(), "orgname", jsonObject1.getString("orgname"));
                                SPUtils.putString(App.getInstance(), "orgname", jsonObject1.getString("orgname"));
                                onClickListener.onClick(ret);
                            } else {
                                ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

}
