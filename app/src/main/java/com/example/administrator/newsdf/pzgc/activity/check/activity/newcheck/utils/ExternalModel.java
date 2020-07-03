package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils;


import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckDetailBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ExternalCheckListBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.SafetyCheck;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class ExternalModel {

    public void getsafetychecklistbyapp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.getHttp(ExternalApi.GETSAFETYCHECKLISTBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<ExternalCheckListBean> mData = com.alibaba.fastjson.JSONArray.parseArray(data.toString(), ExternalCheckListBean.class);
                        adapter.onsuccess(mData);
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
            }
        });
    }

    /**
     * 获取外业实体数据（此接口包含外业实体数据、权限数据、分数面板数据）
     */
    public void getSafetyCheck(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.getHttp(ExternalApi.GETSAFETYCHECK, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        Map<String, Object> map1 = new HashMap<>();
                        JSONObject data = jsonObject.getJSONObject("data");
                        //权限数据
                        String permiss = data.getJSONObject("permission").toString();
                        CheckNewBean.permission permission = com.alibaba.fastjson.JSONObject.parseObject(permiss, CheckNewBean.permission.class);
                        map1.put("permission", permission);
                        //分数面板数据
                        String scord = data.getString("scorePane").toString();
                        List<CheckNewBean.scorePane> list = com.alibaba.fastjson.JSONArray.parseArray(scord, CheckNewBean.scorePane.class);
                        map1.put("scorePane", list);
                        String safetyCheck = data.getJSONObject("safetyCheck").toString();
                        SafetyCheck beans = com.alibaba.fastjson.JSONObject.parseObject(safetyCheck, SafetyCheck.class);
                        map1.put("safetyCheck", beans);
                        adapter.onsuccess(map1);
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
            }
        });
    }


    /**
     * 说明：保存外业检查主数据
     * 创建时间： 2020/7/2 0002 14:41
     *
     * @author winelx
     */
    public void saveSafetyCheckByApp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.postHttp(ExternalApi.SAVESAFETYCHECKBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        String id = jsonObject.getJSONObject("data").getString("id");
                        String status = jsonObject.getJSONObject("data").getString("status");
                        CheckType bean = new CheckType(status, id);
                        adapter.onsuccess(bean);
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                        adapter.onerror();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.onerror();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
                adapter.onerror();
            }
        });
    }

    /**
     * 说明：获取外业检查细项实体结对
     * 创建时间： 2020/7/3 0003 15:24
     *
     * @author winelx
     */
    public void getSafetyCheckDelByApp(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.getHttp(ExternalApi.GETSAFETYCHECKDELBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        Map<String, Object> map = new HashMap<>(3);
                        CheckDetailBean.Project project = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), CheckDetailBean.Project.class);
                        map.put("project", project);
                        CheckDetailBean.Company company = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), CheckDetailBean.Company.class);
                        map.put("project", company);
                        CheckDetailBean.Group group = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), CheckDetailBean.Group.class);
                        map.put("project", group);
                        adapter.onsuccess(map);
                    }else {
                        adapter.onerror(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                adapter.onerror("请求失败");
            }
        });
    }

    /**
     * 说明：获取面板分数
     * 创建时间： 2020/7/3 0003 15:46
     *
     * @author winelx
     */
    public void getScorePane(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.getHttp(ExternalApi.GETSCOREPANE, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<CheckNewBean.scorePane> list = com.alibaba.fastjson.JSONArray.parseArray(data.toString(), CheckNewBean.scorePane.class);
                        adapter.onsuccess(list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }

}
