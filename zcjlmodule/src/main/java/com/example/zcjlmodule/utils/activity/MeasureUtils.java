package com.example.zcjlmodule.utils.activity;

import android.view.View;

import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.treeView.ZhengcTreeListViewAdapters;
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

    private List<OrgBeans> mDatas2 = new ArrayList<>();
    private List<OrgenBeans> mData = new ArrayList<>();
    private boolean status = true;

    /**
     * 征拆类型   JSONObject jsonObject = new JSONObject(s);
     * JSONObject data = jsonObject.getJSONObject("data");
     * JSONArray jsonArray1=data.getJSONArray("ZCLX");
     */
    public void collectiontype(String orgId, final TypeOnClickListener Listener) {
        OkGo.get(Api.GETLEVYINITINFO)
                .params("orgId", orgId)
                .params("type", "ZCLX")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDatas2.clear();
                        mData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray jsonArray1 = data.getJSONArray("ZCLX");
                            if (jsonArray1.length() > 0) {
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
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
                                    mData.add(new OrgenBeans(Id, parentId, name, type));
                                    Listener.onsuccess(mDatas2,mData);
                                }
                                if (status) {
                                    //拿到所有的ID
                                    final ArrayList<String> IDs = new ArrayList<String>();
                                    for (int i = 0; i < mData.size(); i++) {
                                        IDs.add(mData.get(i).getId());
                                    }
                                    //循环集合
                                    for (int i = 0; i < mData.size(); i++) {
                                        //取出父ID，
                                        String pernID = mData.get(i).getParentId();
                                        //用ID判断是否有父级相同的
                                        if (IDs.contains(pernID)) {
                                            //存在相同的的不处理
                                        } else {
                                            //不存在相同的当做第一级
                                            mDatas2.add(new OrgBeans(1, 0, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType()));
                                            Listener.onsuccess(mDatas2,mData);
                                        }
                                    }
                                }
                            } else {
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

    public interface OnClickListener {
        void onsuccess(List<AttachProjectBean> list);

        void onerror();
    }

    public interface TypeOnClickListener {
        void onsuccess(List<OrgBeans> data, List<OrgenBeans> data2);

        void onerror();
    }


}
