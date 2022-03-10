package com.example.administrator.yanghu.pzgc.activity.home.utils;

import com.example.administrator.yanghu.pzgc.adapter.CompleteBean;
import com.example.administrator.yanghu.pzgc.adapter.NoticedBean;
import com.example.administrator.yanghu.pzgc.bean.AgencyBean;
import com.example.administrator.yanghu.pzgc.bean.Audio;
import com.example.administrator.yanghu.pzgc.bean.LastmonthBean;
import com.example.administrator.yanghu.pzgc.bean.Proclamation;
import com.example.administrator.yanghu.pzgc.bean.TodayBean;
import com.example.administrator.yanghu.pzgc.bean.TodayDetailsBean;
import com.example.administrator.yanghu.pzgc.bean.TotalBean;
import com.example.administrator.yanghu.pzgc.bean.TotalDetailsBean;
import com.example.administrator.yanghu.pzgc.utils.Enums;
import com.example.administrator.yanghu.pzgc.utils.HomeApi;
import com.example.administrator.yanghu.pzgc.utils.ListJsonUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;

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
 * @author lx
 * @data :2019/3/12 0012
 * @描述 : 消息首页及关联的界面接口
 * @see
 */
public class HomeFragmentUtils {
    /**
     * 消息通知
     */
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
                                JSONObject data;
                                try {
                                    data = jsonObject.getJSONObject("data");
                                    JSONArray array;
                                    try {
                                        array = data.getJSONArray("results");
                                        list = ListJsonUtils.getListByArray(NoticedBean.class, array.toString());
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("notice", list);
                                        callBack.onsuccess(map);
                                    } catch (Exception e) {
                                    }
                                } catch (Exception e) {
                                }
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

    /**
     * 获取我的待办
     */
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
                                Map<String, Object> map = new HashMap<>();
                                JSONObject data;
                                try {
                                    data = jsonObject.getJSONObject("data");
                                    JSONArray array = data.getJSONArray("results");
                                    list = ListJsonUtils.getListByArray(AgencyBean.class, array.toString());
                                    map.put("agency", list);
                                } catch (Exception e) {
                                }
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

    /**
     * 获取已办事项
     */
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

    /**
     * 消息首页
     */
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
                                JSONObject data;
                                try {
                                    data = jsonObject.getJSONObject("data");
                                } catch (Exception e) {
                                    data = new JSONObject();
                                }
                                map.put("lastCount", data.getString("lastMonthNoticeCount"));
                                map.put("grandCount", data.getString("grandTaskFinishCount"));
                                map.put("todayCount", data.getString("todayTaskFinishCount"));
                                ArrayList<Audio> list = new ArrayList<>();
                                JSONArray orgRanke;
                                try {
                                    orgRanke = data.getJSONArray("orgRanke");
                                    for (int i = 0; i < orgRanke.length(); i++) {
                                        JSONObject json = orgRanke.getJSONObject(i);
                                        list.add(new Audio(json.getString("name"), json.getString("score")));
                                    }
                                    map.put("orgRanke", list);
                                } catch (Exception e) {
                                }
                                /*我的待办*/
                                JSONObject myNewNoTask;
                                try {
                                    myNewNoTask = data.getJSONObject("myNewNoTask");
                                    AgencyBean agencyBean = com.alibaba.fastjson.JSONObject.parseObject(myNewNoTask.toString(), AgencyBean.class);
                                    map.put("agency", agencyBean);
                                } catch (Exception e) {
                                    myNewNoTask = new JSONObject();
                                }
                                /*我的已办*/
                                JSONObject myNewYesTask;
                                try {
                                    myNewYesTask = data.getJSONObject("myNewYesTask");
                                    CompleteBean completeBean = com.alibaba.fastjson.JSONObject.parseObject(myNewYesTask.toString(), CompleteBean.class);
                                    map.put("complete", completeBean);
                                } catch (Exception e) {
                                    myNewYesTask = new JSONObject();
                                }
                                /*最新消息*/
                                JSONObject myNewNotice;
                                try {
                                    myNewNotice = data.getJSONObject("myNewNotice");
                                    NoticedBean noticedBean = com.alibaba.fastjson.JSONObject.parseObject(myNewNotice.toString(), NoticedBean.class);
                                    map.put("noticed", noticedBean);
                                } catch (Exception e) {
                                    myNewNotice = new JSONObject();
                                }
                                /*通知公告*/
                                JSONObject proclamation;
                                try {
                                    proclamation = data.getJSONObject("proclamation");
                                    Proclamation proclamation1 = com.alibaba.fastjson.JSONObject.parseObject(proclamation.toString(), Proclamation.class);
                                    map.put("proclamation", proclamation1);
                                } catch (Exception e) {

                                }
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror(Enums.REQUEST_ERROR);
                    }
                });
    }

    /**
     * 通知单获取 分公司统计 及 标段统计 数据接口
     */
    public static void getNoticeCountData(String id, final requestCallBack callBack) {
        GetRequest request = OkGo.get(HomeApi.GETNOTICECOUNTDATA);
        if (id != null) {
            request.params("fOrgId1", id);
        }
        request
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONObject data;
                                try {
                                    data = jsonObject.getJSONObject("data");
                                    JSONArray result = data.getJSONArray("results");
                                    List<LastmonthBean> list = ListJsonUtils.getListByArray(LastmonthBean.class, result.toString());
                                    map.put("lastmonth", list);
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

    /**
     * 累计任务列表
     */
    public static void cumulativeRequest(final requestCallBack callBack) {
        OkGo.get(HomeApi.GETGRANDTASKFINISHBYF)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONArray data;
                                try {
                                    data = jsonObject.getJSONArray("data");
                                    List<TotalBean> list = ListJsonUtils.getListByArray(TotalBean.class, data.toString());
                                    map.put("total", list);
                                } catch (Exception e) {
                                }
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

    /**
     * 今日任务列表
     */
    public static void todayRequest(final requestCallBack callBack) {
        OkGo.get(HomeApi.GETTODAYTASKFINISHBYF)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONArray data;
                                try {
                                    data = jsonObject.getJSONArray("data");
                                    List<TodayBean> list = ListJsonUtils.getListByArray(TodayBean.class, data.toString());
                                    map.put("today", list);
                                } catch (Exception e) {
                                }
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

    /**
     * 累计任务标段列表
     */
    public static void grandTaskFinish(String id, final requestCallBack callBack) {
        OkGo.get(HomeApi.getgrandandtodaytaskbyb)
                .params("fOrgId", id)
                .params("isToday", 2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONArray data = jsonObject.getJSONArray("data");
                                List<TotalDetailsBean> list = ListJsonUtils.getListByArray(TotalDetailsBean.class, data.toString());
                                map.put("list", list);
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

    /**
     * 今日任务标段列表
     */
    public static void todayDetailsRequest(String id, final requestCallBack callBack) {
        OkGo.get(HomeApi.getgrandandtodaytaskbyb)
                .params("fOrgId", id)
                .params("isToday", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONArray data = jsonObject.getJSONArray("data");
                                List<TodayDetailsBean> list = ListJsonUtils.getListByArray(TodayDetailsBean.class, data.toString());
                                map.put("list", list);
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

    /**
     * @author lx
     * @data :2019/3/13 0013
     * @描述 :点击事件接口
     */
    public interface requestCallBack {
        void onsuccess(Map<String, Object> map);

        void onerror(String string);
    }

}
