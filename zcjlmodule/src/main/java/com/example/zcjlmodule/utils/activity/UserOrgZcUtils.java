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

import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * description:
 *
 * @author lx
 *         date: 2018/10/25 0025 上午 10:46
 *         update: 2018/10/25 0025
 *         version:
 *         切换组织
 *         activity：UserOrgZcActivity
 */
public class UserOrgZcUtils {
    private boolean status = true;
    private List<OrgBeans> mDatas2 = new ArrayList<>();
    private List<OrgenBeans> mData = new ArrayList<>();

    private List<OrgBeans> OrgsData2 = new ArrayList<>();
    private List<OrgenBeans> OrgsData = new ArrayList<>();
    /**
     *  获取数据
     */
    public interface OnClickListener {
        /**
         * 处理数据后返回
         * @param data  请求返回的数据集合（在界面点击展开后，data2在此集合中获取要展示的数据）
         * @param data2 界面数据展示的集合
         */
        void onClick(List<OrgBeans> data, List<OrgenBeans> data2);
    }
    /**
     *  切换组织的
     */
    public interface OnChangeClickListener {
        /**
         * 切换组织点击事件
         * @param ret 状态（0成功）
         */
        void onClick(int ret);
    }


    /**
     * 获取组织数据
     * @param onClickListener 点击事件接口
     */
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
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
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
                                        mDatas2.add(new OrgBeans(1, 0, json.getString("name"), id, parentId, type));
                                    }
                                    String name;
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        name = "";
                                    }
                                    mData.add(new OrgenBeans(id, parentId, name, type));
                                    onClickListener.onClick(mDatas2, mData);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 切换组织
     * @param orgId 组织ID
     * @param orgName 组织名称
     * @param onClickListener  点击事件接口
     */
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

    /**
     * 获取组织数据
     * @param onClickListener 点击事件接口
     */
    public void getOrgs(final OnClickListener onClickListener) {
        OkGo.get(Api.GETORGS)
                .params("types","3")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            if (jsonArray1.length() > 0) {
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
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
                                        OrgsData2.add(new OrgBeans(1, 0, json.getString("name"), id, parentId, type));
                                    }
                                    String name;
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        name = "";
                                    }
                                    if (status){
                                        //不存在相同的当做第一级
                                        OrgsData2.add(new OrgBeans(1, 0, json.getString("name"), id, parentId, type));
//                                        //拿到所有的ID
//                                        final ArrayList<String> IDs = new ArrayList<String>();
//                                        for (int j = 0; j <mData.size() ; j++) {
//                                            IDs.add(mData.get(i).getId());
//                                        }
//                                        for (int j = 0; j < mData.size() ; j++) {
//                                            //取出父ID，
//                                            String pernID = mData.get(i).getParentId();
//                                            //用ID判断是否有父级相同的
//                                            if (IDs.contains(pernID)) {
//                                                //存在相同的的不处理
//                                            } else {
                                                                                            onClickListener.onClick(OrgsData2, OrgsData);
//                                            }
                                      //  }
                                    }else {
                                        OrgsData.add(new OrgenBeans(id, parentId, name, type));
                                        onClickListener.onClick(OrgsData2, OrgsData);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
