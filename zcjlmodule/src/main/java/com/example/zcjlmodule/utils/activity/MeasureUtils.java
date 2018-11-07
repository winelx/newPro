package com.example.zcjlmodule.utils.activity;

import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/7 0007.
 * @description:新增原始勘丈表的所属组织，指挥部等的帮助类
 */

public class MeasureUtils {

    /**
     * 所有组织
     */
    public void ascriptionOrg(String orgId, final OnClickListener Listener) {
        OkGo.get(Api.GETLEVYINITINFO)
                .params("orgId", orgId)
                .params("type", "SSXM")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<AttachProjectBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (s.contains("data")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONArray jsonArray = data.getJSONArray("SSXM");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        list.add(new AttachProjectBean(id, name));
                                    }
                                }
                                Listener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Listener.onerror();
                    }
                });
    }

    /**
     * 所属标段
     */
    public void ascriptionbids(String orgId, final OnClickListener Listener) {
        OkGo.get(Api.GETLEVYINITINFO)
                .params("orgId", orgId)
                .params("type", "SSBD")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<AttachProjectBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (s.contains("data")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONArray jsonArray = data.getJSONArray("SSBD");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        list.add(new AttachProjectBean(id, name));
                                    }
                                }
                                Listener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Listener.onerror();
                    }
                });
    }

    /**
     * 所属指挥部
     */
    public void ascriptionzhb(String orgId, final OnClickListener Listener) {
        OkGo.get(Api.GETLEVYINITINFO)
                .params("orgId", orgId)
                .params("type", "ZHB")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<AttachProjectBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (s.contains("data")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONArray jsonArray = data.getJSONArray("ZHB");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        list.add(new AttachProjectBean(id, name));
                                    }
                                }
                                Listener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Listener.onerror();
                    }
                });
    }

    /**
     * 申报期数
     */
    public void ascriptionsbqs(String orgId, final OnClickListener Listener) {
        OkGo.get(Api.GETLEVYINITINFO)
                .params("orgId", orgId)
                .params("type", "SBQS")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<AttachProjectBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (s.contains("data")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONArray jsonArray = data.getJSONArray("SBQS");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        list.add(new AttachProjectBean(id, name));
                                    }
                                }
                                Listener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Listener.onerror();
                    }
                });
    }

    /**
     * 区域
     */
    public void ascriptionqyxx(String orgId, final OnClickListener Listener) {
        OkGo.get(Api.GETLEVYINITINFO)
                .params("orgId", orgId)
                .params("type", "QYXX")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<AttachProjectBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (s.contains("data")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONArray jsonArray = data.getJSONArray("QYXX");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        list.add(new AttachProjectBean(id, name));
                                    }
                                }
                                Listener.onsuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Listener.onerror();
                    }
                });
    }

    /**
     * 征拆类型
     */
    public void collectiontype(String orgId, final TypeOnClickListener Listener) {
                OkGo.get(Api.GETLEVYINITINFO)
                        .params("orgId", orgId)
                        .params("type", "ZCLX")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {

                            }
                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });
    }

    public interface OnClickListener {
        void onsuccess(List<AttachProjectBean> list);

        void onerror();
    }
    public interface TypeOnClickListener {
        void onsuccess( List<OrgenBeans> mData);

        void onerror();
    }

}
