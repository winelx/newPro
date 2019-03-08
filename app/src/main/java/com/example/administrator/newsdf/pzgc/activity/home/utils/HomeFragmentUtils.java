package com.example.administrator.newsdf.pzgc.activity.home.utils;

import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CompleteBean;
import com.example.administrator.newsdf.pzgc.Adapter.NoticedBean;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.administrator.newsdf.pzgc.utils.HomeApi;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;

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

public class HomeFragmentUtils {
    /*消息通知*/
    public static void mysystemnotice(int page, final requestCallBack callBack) {
        OkGo.get(HomeApi.MYSYSTEMNOTICE)
                .params("page", page)
                .params("rows", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                List<NoticedBean> list = new ArrayList<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray array = data.getJSONArray("results");
                                list = ListJsonUtils.getListByArray(NoticedBean.class, array.toString());
                                Map<String, Object> map = new HashMap<>();
                                map.put("notice", list);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror(Enums.ANALYSIS_ERROR);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror(Enums.REQUEST_ERROR);
                    }
                });
    }

    /*获取我的待办*/
    public static void mynotast(int page, final requestCallBack callBack) {
        OkGo.get(HomeApi.MYNOTAST)
                .params("page", page)
                .params("rows", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                List<AgencyBean> list = new ArrayList<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray array = data.getJSONArray("results");
                                list = ListJsonUtils.getListByArray(AgencyBean.class, array.toString());
                                Map<String, Object> map = new HashMap<>();
                                map.put("agency", list);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror(Enums.ANALYSIS_ERROR);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /*获取已办事项*/
    public static void myyestast(int page, final requestCallBack callBack) {
        OkGo.get(HomeApi.MYYESTAST)
                .params("page", page)
                .params("rows", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                List<CompleteBean> list = new ArrayList<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray array = data.getJSONArray("results");
                                list = ListJsonUtils.getListByArray(CompleteBean.class, array.toString());
                                Map<String, Object> map = new HashMap<>();
                                map.put("complete", list);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror(Enums.ANALYSIS_ERROR);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /*消息首页*/
    public static void getmsgnoticepagedata(final requestCallBack callBack) {
        OkGo.get(HomeApi.GETMSGNOTICEPAGEDATA)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            Map<String, Object> map = new HashMap<>();
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                /*我的待办*/
                                JSONObject myNewNoTask;
                                try {
                                    myNewNoTask = data.getJSONObject("myNewNoTask");
                                    AgencyBean agencyBean = com.alibaba.fastjson.JSONObject.parseObject(myNewNoTask.toString(), AgencyBean.class);
                                    map.put("agency", agencyBean);
                                } catch (Exception e) {
                                }
                                /*我的已办*/
                                JSONObject myNewYesTask;
                                try {
                                    myNewYesTask = data.getJSONObject("myNewYesTask");
                                    CompleteBean completeBean = com.alibaba.fastjson.JSONObject.parseObject(myNewYesTask.toString(), CompleteBean.class);
                                    map.put("complete", completeBean);
                                } catch (Exception e) {
                                }
                                /*最新消息*/
                                JSONObject myNewNotice;
                                try {
                                    myNewNotice = data.getJSONObject("myNewNotice");
                                    NoticedBean noticedBean = com.alibaba.fastjson.JSONObject.parseObject(myNewNotice.toString(), NoticedBean.class);
                                    map.put("noticed", noticedBean);
                                } catch (Exception e) {
                                }
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }

                        } catch (Exception e) {
                            callBack.onerror(Enums.ANALYSIS_ERROR);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror(Enums.REQUEST_ERROR);
                    }
                });
    }

    public interface requestCallBack {
        void onsuccess(Map<String, Object> map);

        void onerror(String string);
    }

}
