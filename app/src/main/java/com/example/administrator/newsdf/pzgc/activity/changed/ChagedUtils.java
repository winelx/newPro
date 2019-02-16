package com.example.administrator.newsdf.pzgc.activity.changed;

import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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

public class ChagedUtils {

    /*获取列表数据*/
    public void getcnflist(boolean lean, int status, String orgId, int page, final CallBack callBack) {
        GetRequest str = OkGo.get(Requests.GETCNFLIST);
        //如果status==-1；
        if (status != -1) {
            str.params("status", status);
        }
        //true:全部，false：我的
        str.params("isAll", lean)
                //组织Id
                .params("orgId", orgId)
                //页数
                .params("page", page)
                //每页长度
                .params("size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray results = data.getJSONArray("results");
                                List<ChagedList> lists = ListJsonUtils.getListByArray(ChagedList.class, results.toString());
                                Map<String, Object> map = new HashMap<>();
                                map.put("list", lists);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*获取整改通知单主要信息*/

    /**
     * @param id       单据Id
     * @param callBack
     */
    public void getNoticeFormMainInfo(String id, final NoticeFormMainInfoCallback callBack) {
        OkGo.get(Requests.GETNOTICEFORMMAININFO)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                ArrayList<Object> list = new ArrayList<>();
                                ChagedNoticeDetails item = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), ChagedNoticeDetails.class);
                                list.add(item);
                                callBack.onsuccess(list);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*删除该项问题*/

    /**
     * @param deleteNoticeDel 单据Id
     * @param callBack
     */
    public void setdelete(String deleteNoticeDel, final CallBacks callBack) {
        OkGo.get(Requests.DELETENOTICEDEL)
                .params("deleteNoticeDel", deleteNoticeDel)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBack.onsuccess("删除成功");
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*指派*/

    /**
     * @param userId    指派人Id
     * @param billsId   单据Id
     * @param callBacks
     * @ assignDate 指派时间
     * @ remarks  备注
     * @ motionNode 运动节点
     */
    public void setassignPage(String userId, String billsId, final CallBacks callBacks) {
        OkGo.get(Requests.ASSIGNPAGE)
                .params("id", billsId)
                .params("assignPerson", userId)
                .params("assignDate", Dates.getDate())
                .params("remarks", "安卓")
                .params("motionNode", "安卓")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBacks.onsuccess("指派成功");
                            } else {
                                callBacks.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBacks.onerror("请求失败");
                    }
                });
    }

    /*下发、我回复*/

    /**
     * @param noticeId   整改通知单id
     * @param dealId     当前处理节点Id，在getNoticeFormMainInfo这个接口中有返回
     * @param optionType 操作类型（1：下发，2：我来回复）
     * @param callBacks
     * @ motionNode //运动节点，在getNoticeFormMainInfo这个接口中有返回
     */
    public void setsenddata(String noticeId, String dealId, int optionType, final CallBacks callBacks) {
        OkGo.get(Requests.SENDDATA)
                .params("noticeId", noticeId)
                .params("dealId", dealId)
                .params("motionNode", "motionNode")
                .params("optionType", optionType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBacks.onsuccess("操作成功");
                            } else {
                                callBacks.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBacks.onerror("请求失败");
                    }
                });
    }


    /*保存、修改整改通知单*/

    /**
     * id  新增时为空
     * rectificationOrgid	整改组织id
     * rectificationPerson	整改负责人id
     * 整改负责人id sendOrgid
     * 下发组织id
     */
    public void setsavenoticeform(String id, String rectificationOrgid, String rectificationPerson, String sendOrgid, final CallBacks callBacks) {
        OkGo.get(Requests.SENDDATA)
                .params("id", id)
                .params("rectificationOrgid", rectificationOrgid)
                .params("rectificationPerson", rectificationPerson)
                .params("sendOrgid", sendOrgid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBacks.onsuccess("操作成功");
                            } else {
                                callBacks.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBacks.onerror("请求失败");
                    }
                });
    }

    public interface CallBack {
        void onsuccess(Map<String, Object> map);

        void onerror(String str);
    }

    public interface NoticeFormMainInfoCallback {
        void onsuccess(ArrayList<Object> list);

        void onerror(String str);
    }

    public interface CallBacks {
        void onsuccess(String string);

        void onerror(String string);
    }
}
