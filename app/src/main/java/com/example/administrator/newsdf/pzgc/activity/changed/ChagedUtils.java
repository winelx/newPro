package com.example.administrator.newsdf.pzgc.activity.changed;

import com.example.administrator.newsdf.pzgc.bean.ChagedImportitem;
import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;
import com.example.administrator.newsdf.pzgc.bean.ChagedProblembean;
import com.example.administrator.newsdf.pzgc.bean.Checkitem;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsChaged;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsProblem;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsRecord;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.baselibrary.bean.photoBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{\}
 * 描述：MainActivity
 * {@link }
 */
public class ChagedUtils implements Serializable {

    /*获取列表数据*/

    /**
     * @param lean     //true:全部，false：我的
     * @param status   请求状态
     * @param orgId    组织ID
     * @param page     页数
     * @param callBack
     */
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
                            callBack.onerror("数据解析失败");
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
    public void getNoticeFormMainInfo(String id, final CallBack callBack) {
        OkGo.get(Requests.GETNOTICEFORMMAININFO)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            Map<String, Object> map = new HashMap<>();
                            if (jsonObject.getInt("ret") == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                ChagedNoticeDetails item = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), ChagedNoticeDetails.class);
                                int permission = item.getPermission();
                                map.put("bean", item);
                                JSONArray details = data.getJSONArray("details");
                                List<ChagedNoticeDetailslsit> list1 = ListJsonUtils.getListByArray(ChagedNoticeDetailslsit.class, details.toString());
                                List<ChagedNoticeDetailslsit> list2 = ListJsonUtils.getListByArray(ChagedNoticeDetailslsit.class, details.toString());
                                for (int i = 0; i < list1.size(); i++) {
                                    String name = list1.get(i).getStandardDelName();
                                    list1.get(i).setStandardDelName("第" + (i + 1) + "项问题    " + name);
                                    list2.get(i).setStandardDelName((i + 1) + "、" + name);
                                }
                                map.put("list", list1);
                                map.put("list2", list2);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*删除通知单问题项*/

    /**
     * @param noticeDelId 问题项Id
     * @param noticeId    通知单Id
     * @param callBack
     */
    public void deleteNoticeDel(String noticeDelId, String noticeId, final CallBacks callBack) {
        OkGo.post(Requests.DELETENOTICEDEL)
                .params("noticeDelId", noticeDelId)
                .params("noticeId", noticeId)
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
                            callBack.onerror("数据解析失败");
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
     * @param orgId     指派人Id
     * @param callBacks
     * @ assignDate 指派时间
     * @ remarks  备注
     * @ motionNode 运动节点
     */
    public void setassignPage(String userId, String billsId, String motionNode, String orgId, final CallBacks callBacks) {
        OkGo.post(Requests.ASSIGNPAGE)
                .params("id", billsId)
                .params("assignPerson", userId)
                .params("rectificationOrgid", orgId)
                .params("assignDate", Dates.getDate())
                .params("remarks", "安卓")
                .params("motionNode", motionNode)
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
                            callBacks.onerror("数据解析失败");
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
    public void setsenddata(String noticeId, String dealId, String motionNode, int optionType, final CallBacks callBacks) {
        OkGo.post(Requests.SENDDATA)
                .params("noticeId", noticeId)
                .params("dealId", dealId)
                .params("motionNode", motionNode)
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
                            callBacks.onerror("数据解析失败");
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
    public void setsavenoticeform(final String id, String rectificationOrgid, String rectificationPerson, String sendOrgid, final CallBack callBacks) {
        PostRequest request = OkGo.post(Requests.SAVENOTICEFORM);
        request.params("id", id);
        request.params("rectificationOrgid", rectificationOrgid)
                .params("rectificationPerson", rectificationPerson)
                .params("sendOrgid", sendOrgid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                String str = data.getString("id");
                                String code = data.getString("code");
                                map.put("id", str);
                                map.put("code", code);
                                callBacks.onsuccess(map);
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

    /*删除整改通知单*/

    /**
     * @param id        id
     * @param callBacks
     */
    public void deletebill(String id, final CallBacks callBacks) {
        OkGo.post(Requests.DELETENOTICE)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBacks.onsuccess("成功");
                            } else {
                                callBacks.onsuccess(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBacks.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBacks.onerror("请求失败");
                    }
                });

    }

    /*保存、修改问题项*/

    /**
     * @param map       参数mao
     * @param photoList 图片集合
     */
    public void saveNoticeDetails(Map<String, String> map, ArrayList<File> photoList, final CallBack callBacks) {
        PostRequest request = OkGo.post(Requests.SAVENOTICEDETAILS);
        if (photoList.size() > 0) {
            request.addFileParams("attachments", photoList);
        } else {
            //表单提交
            request.isMultipart(true);
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            request.params(entry.getKey(), entry.getValue());
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String id = data.getString("id");
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        ArrayList<photoBean> photoBeanArrayList = new ArrayList<>();
                        JSONArray file = data.getJSONArray("files");
                        for (int i = 0; i < file.length(); i++) {
                            JSONObject jsonObject1 = file.getJSONObject(i);
                            String path = Requests.networks + jsonObject1.getString("filepath");
                            String type = jsonObject1.getString("id");
                            String name = jsonObject1.getString("filename");
                            photoBeanArrayList.add(new photoBean(path, name, type));
                        }
                        map.put("list", photoBeanArrayList);
                        callBacks.onsuccess(map);
                    } else {
                        callBacks.onerror(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBacks.onerror("数据解析失败");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBacks.onerror("请求失败");
            }
        });
    }

    /*导入问题项时查询问题项*/

    /**
     * @param checkManageId 监督检查id，
     * @param callBack
     */
    public void getdetailsofimport(String checkManageId, final CallBack callBack) {
        OkGo.get(Requests.GETDETAILSOFIMPORT)
                .params("checkManageId", checkManageId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray data = jsonObject.getJSONArray("data");
                                ArrayList<Object> mData = new ArrayList<>();
                                List<Checkitem> list = ListJsonUtils.getListByArray(Checkitem.class, data.toString());
                                ArrayList<String> titles = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    //获取所有内类
                                    String name = list.get(i).getName();
                                    if (!titles.contains(name)) {
                                        titles.add(name);
                                    }
                                }
                                for (int i = 0; i < titles.size(); i++) {
                                    //获取类型
                                    String name = titles.get(i);
                                    //判断集合是否存在
                                    if (!mData.contains(name)) {
                                        //不存在添加
                                        mData.add(titles.get(i));
                                    }
                                    //循环所有内容的集合
                                    for (int j = 0; j < list.size(); j++) {
                                        //取出每条数据的类型
                                        String names = list.get(j).getName();
                                        //判断俩个类型是一致
                                        if (name.equals(names)) {
                                            //如果内容相同
                                            mData.add(list.get(j));
                                        }
                                    }
                                }
                                map.put("list", mData);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*导入问题项时查询监督检查列表*/

    /**
     * @param orgId    组织id
     * @param page     页数
     * @param callBack 接口
     */
    public void choosemanagedatalist(String orgId, int page, final CallBack callBack) {
        OkGo.get(Requests.CHOOSEMANAGEDATALIST)
                .params("orgId", orgId)
                .params("page.pn", page)
                .params("page.size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray result = data.getJSONArray("results");
                                ArrayList<ChagedImportitem> list = new ArrayList<>();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject json = result.getJSONObject(i);
                                    //id
                                    String id = isexpty(json.getString("id"));
                                    //title
                                    String title = isexpty(json.getString("name"));
                                    //wbsTaskTypeName
                                    String partDetail;
                                    try {
                                        partDetail = json.getString("partDetails");
                                        if (!partDetail.isEmpty()) {
                                            partDetail = ">>" + partDetail;
                                        }
                                    } catch (Exception e) {
                                        partDetail = "";
                                    }
                                    String wbsTaskTypeName = isexpty(json.getString("wbsTaskTypeName") + partDetail);
                                    //orgName 检查
                                    String checkOrgName = isexpty(json.getString("checkOrgName"));
                                    //checkDate 检查日期
                                    String checkDate = isexpty(json.getString("checkDate"));
                                    //scord
                                    String score = isexpty(json.getString("score"));
                                    //iwork
                                    int iwork = json.getInt("iwork");
                                    //
                                    String realname = json.getJSONObject("checkUser").getString("realname");
                                    list.add(new ChagedImportitem(title, wbsTaskTypeName, score, realname, checkDate, checkOrgName, id, iwork));
                                }
                                map.put("list", list);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (
                                JSONException e)

                        {
                            e.printStackTrace();
                            callBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*保存导入问题项*/

    /**
     * @param noticeId     通知单id
     * @param manageDelIds 数组，逗号隔开。从getDetailsOfImport接口的detailsIds获取
     * @param callBack
     */
    public void batchSaveNoteceDel(String checkManageId, String noticeId, String manageDelIds, final CallBacks callBack) {
        OkGo.post(Requests.BATCHSAVENOTECEDEL)
                .params("noticeId", noticeId)
                .params("checkManageId", checkManageId)
                .params("manageDelIds", manageDelIds)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBack.onsuccess(jsonObject.getString("msg"));
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*通知单保存状态时查询单个问题项详情*/

    public void getDetailsInfoOfSaveStatus(String id, final CallBack callBack) {
        OkGo.get(Requests.GETDETAILSINFOOFSAVESTATUS)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                //获取返回内容
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                ChagedProblembean item = com.alibaba.fastjson.JSONObject.parseObject(jsonObject1.toString(), ChagedProblembean.class);
                                map.put("bean", item);
                                JSONArray file = jsonObject1.getJSONArray("files");
                                ArrayList<photoBean> afterFileslist = new ArrayList<>();
                                for (int i = 0; i < file.length(); i++) {
                                    JSONObject json = file.getJSONObject(i);
                                    String path = Requests.networks + json.getString("filepath");
                                    String type = json.getString("id");
                                    String name = json.getString("filename");
                                    afterFileslist.add(new photoBean(path, name, type));
                                }
                                map.put("list", afterFileslist);
                                callBack.onsuccess(map);
                            } else {
                                callBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*通知单非保存状态时查询单个问题项详情*/

    /**
     * @param id       通知单详情id
     * @param callBack {@link  ChagedNoticeItemDetailsActivity}
     */
    public void getNoticeDetailsInfo(String id, final NoticeFormMainInfoCallback callBack) {
        OkGo.get(Requests.GETNOTICEDETAILSINFO)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            JSONObject data = jsonObject.getJSONObject("data");
                            ArrayList<Object> list = new ArrayList<>();
                            /*整改前*/
                            //整改部位名称
                            String rectificationPartName;
                            try {
                                rectificationPartName = data.getString("rectificationPartName");
                            } catch (Exception e) {
                                rectificationPartName = "";
                            }
                            //整改期限
                            String rectificationDate;
                            try {
                                rectificationDate = data.getString("rectificationDate");
                            } catch (Exception e) {
                                rectificationDate = "";
                            }

                            //违反标准
                            String standardDelName;
                            try {
                                standardDelName = data.getString("standardDelName");
                            } catch (Exception e) {
                                standardDelName = "";
                            }

                            //存在问题
                            String rectificationReason;
                            try {
                                rectificationReason = data.getString("rectificationReason");
                            } catch (Exception e) {
                                rectificationReason = "";
                            }
                            ArrayList<photoBean> afterFileslist = new ArrayList<>();
                            JSONArray afterFiles;
                            try {
                                afterFiles = data.getJSONArray("beforeFiles");
                            } catch (Exception e) {
                                afterFiles = new JSONArray();
                            }
                            for (int i = 0; i < afterFiles.length(); i++) {
                                JSONObject json1 = afterFiles.getJSONObject(i);
                                String photopath = Requests.networks + json1.getString("filepath");
                                String photoname = json1.getString("filename");
                                String phototype = json1.getString("id");
                                afterFileslist.add(new photoBean(photopath, photoname, phototype));
                            }
                            if (afterFileslist.size() > 0) {

                            }
                            list.add(new NoticeItemDetailsProblem(rectificationPartName, rectificationDate, standardDelName, rectificationReason, afterFileslist));


                            /*整改后*/

                            //回复时间
                            String replyDate;
                            try {
                                replyDate = data.getString("replyDate");
                            } catch (Exception e) {
                                replyDate = "";
                            }
                            //整改描述
                            String replyDescription;
                            try {
                                replyDescription = data.getString("replyDescription");
                            } catch (Exception e) {
                                replyDescription = "";
                            }
                            ArrayList<photoBean> beforeFileslist = new ArrayList<>();
                            JSONArray beforeFiles;
                            try {
                                beforeFiles = data.getJSONArray("afterFiles");
                            } catch (Exception e) {
                                beforeFiles = new JSONArray();
                            }
                            for (int i = 0; i < beforeFiles.length(); i++) {
                                JSONObject json1 = beforeFiles.getJSONObject(i);
                                String photopath = Requests.networks + json1.getString("filepath");
                                String photoname = json1.getString("filename");
                                String phototype = json1.getString("fileext");
                                beforeFileslist.add(new photoBean(photopath, photoname, phototype));
                            }
                            //如果回复事件为空，没有回复
                            list.add(new NoticeItemDetailsChaged(replyDate, replyDescription, beforeFileslist));
                            /*操作记录*/
                            JSONArray hisCord = data.getJSONArray("hisCord");
                            List<NoticeItemDetailsRecord> list1 = ListJsonUtils.getListByArray(NoticeItemDetailsRecord.class, hisCord.toString());
                            list.addAll(list1);
                            callBack.onsuccess(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onerror("请求失败");
                    }
                });
    }

    /*指派記錄*/
    public void assignhistory(String noticeId, CallBack callBack) {
        OkGo.get(Requests.GETPROCESSHISCORD)
                .params("noticeId", noticeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                      /*  JSONObject jsonObject try { -new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
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

    public String isexpty(String string) {
        try {
            if (string.isEmpty()) {
                return "";
            } else {
                return string;
            }
        } catch (Exception e) {
            return "";
        }

    }
}
