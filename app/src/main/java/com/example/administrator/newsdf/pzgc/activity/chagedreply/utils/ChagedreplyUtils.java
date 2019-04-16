package com.example.administrator.newsdf.pzgc.activity.chagedreply.utils;

import android.text.TextUtils;

import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ChagedReply;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ChagedreplyList;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.Chagereplydetails;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ImprotItem;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.RelationList;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ReplyBillBean;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsRecord;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsRecord;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsText;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/18 0018}
 * 描述：整改回复单网络请求帮助类
 * {@link }
 */
public class ChagedreplyUtils {


    /*获取列表数据*/

    /**
     * @param isAll  true:全部，false：我的
     * @param orgId  组织id
     * @param
     * @param status 状态，非必须，为空时查询全部，0：保存；1：验证中；2:已完成；3：打回；20：未处理；30：已处理
     * @param page   第几页
     */
    public static void getCRFList(boolean isAll, int status, String orgId, int page, final MapCallBack callBack) {
        PostRequest str = OkGo.post(Requests.GETCRFLIST);
        //如果status==-1；
        if (status != -1) {
            str.params("status", status);
        }
        //true:全部，false：我的
        str.params("isAll", isAll)
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
                                List<ChagedreplyList> lists = ListJsonUtils.getListByArray(ChagedreplyList.class, results.toString());
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

    /*获取保存状态的整改验证单*/
    public static void getReplyFormOfSaveStatus(String id, final MapCallBack callBack) {
        OkGo.get(Requests.GETREPLYFORMOFSAVESTATUS)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> content = new HashMap<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                ChagedReply chagedReply = (ChagedReply) com.alibaba.fastjson.JSONObject.parseObject(data.toString(), ChagedReply.class);
                                content.put("bean", chagedReply);
                                JSONArray details;
                                try {
                                    details = data.getJSONArray("details");
                                } catch (Exception e) {
                                    details = new JSONArray();
                                }
                                List<Chagereplydetails> list = ListJsonUtils.getListByArray(Chagereplydetails.class, details.toString());
                                int count = 0;
                                for (int i = 0; i < list.size(); i++) {
                                    int isreply = list.get(i).getIsReply();
                                    if (isreply == 1) {
                                        count++;
                                    }
                                }
                                content.put("list", list);
                                content.put("count", count);
                                callBack.onsuccess(content);
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

    /*创建整改验证单*/
    public static void createReplyForm(final Map<String, String> map, final MapCallBack callBack) {
        PostRequest request = OkGo.post(Requests.CREATEREPLYFORM);
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
                        Map<String, Object> content = new HashMap<>();
                        try {
                            JSONObject data = jsonObject.getJSONObject("data");
                            content.put("id", data.getString("id"));
                            String code;
                            try {
                                code = data.getString("code");
                            } catch (Exception e) {
                                code = "";
                            }

                            content.put("code", code);
                        } catch (Exception e) {
                        }
                        callBack.onsuccess(content);
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

    /*创建整改验证单时选择整改通知单*/

    /**
     * @param orgId        组织ID
     * @param page         页数
     * @param listCallback
     */
    public static void getNoticeFormList(String orgId, int page, final MapCallBack listCallback) {
        OkGo.get(Requests.GETNOTICEFORMLIST)
                .params("orgId", orgId)
                .params("page.size", 20)
                .params("page.pn", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            Map<String, Object> map = new HashMap<>();
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray result = data.getJSONArray("results");
                                List<RelationList> list = ListJsonUtils.getListByArray(RelationList.class, result.toString());
                                map.put("Relation", list);
                                listCallback.onsuccess(map);
                            } else {
                                listCallback.onerror(jsonObject.getString("msg"));
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

    /*查询导入问题列表*/

    /**
     * @param noticeId    整改通知单id
     * @param mapCallBack 接口
     */
    public static void chooseNoticeDelData(String noticeId, final MapCallBack mapCallBack) {
        OkGo.get(Requests.CHOOSENOTICEDELDATA)
                .params("noticeId", noticeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray result = data.getJSONArray("results");
                                List<ImprotItem> list = ListJsonUtils.getListByArray(ImprotItem.class, result.toString());
                                Map<String, Object> map = new HashMap<>();
                                map.put("list", list);
                                mapCallBack.onsuccess(map);
                            } else {
                                mapCallBack.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mapCallBack.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mapCallBack.onerror("请求失败");
                    }
                });
    }

    /*保存导入问题项*/

    /**
     * rows	是
     * 从chooseNoticeDelData接口获取，问题项id，可以多选，传递给后台时key为rows，逗号隔开
     * noticeIds
     * 从chooseNoticeDelData接口获取，整改通知单id，可以多选，传递给后台时key为noticeIds，逗号隔开
     * replyId
     * 回复单id
     */
    public static void batchSaveReplyDel(String noticeIds, String id, String rows, final ObjectCallBacks callBack) {
        OkGo.post(Requests.BATCHSAVEREPLYDEL)
                .params("rows", rows)
                .params("noticeIds", noticeIds)
                .params("replyId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBack.onsuccess("");
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


    /*获取整改验证单数据详情*/

    /**
     * @param replyId 回复验证单id
     */
    public static void getReplyDelData(String replyId, final MapCallBack callBack) {
        OkGo.post(Requests.GETREPLYDELDATA)
                .params("replyId", replyId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();

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
                    }
                });
    }

    /*删除整改验证单详情*/

    /**
     * @param replyDelId      回复验证单详情id
     * @param replyId         回复验证单id
     * @param objectCallBacks
     */
    public static void deleteReplyDel(String replyDelId, String replyId, final ObjectCallBacks objectCallBacks) {
        OkGo.post(Requests.DELETEREPLYDEL)
                .params("replyDelId", replyDelId)
                .params("replyId", replyId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                objectCallBacks.onsuccess("");
                            } else {
                                objectCallBacks.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            objectCallBacks.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        objectCallBacks.onerror("请求失败");
                    }
                });
    }

    /*获取非保存状态的回复验证单主要信息*/

    /**
     * @param id       整改验证单id
     * @param callBack
     */
    public static void getReplyFormMainInfo(String id, String noticeId, final ListCallback callBack) {
        GetRequest string = OkGo.get(Requests.GETREPLYFORMMAININFO)
                .params("id", id);
        if (!TextUtils.isEmpty(noticeId)) {
            string.params("noticeDelId", noticeId);
        }
        string.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        ArrayList<Object> list = new ArrayList<>();
                        //解析实体
                        ReplyDetailsContent content = com.alibaba.fastjson.JSONObject.parseObject(jsonObject1.toString(), ReplyDetailsContent.class);
                        list.add(content);
                        JSONArray details = jsonObject1.getJSONArray("details");
                        //问题项
                        if (details.length() > 0) {
                            list.add(new ReplyDetailsText("问题项"));
                            List<ReplyDetailsRecord> Detailslist = ListJsonUtils.getListByArray(ReplyDetailsRecord.class, details.toString());
                            list.addAll(Detailslist);
                        }
                        //处理记录
                        JSONArray hisCord = jsonObject1.getJSONArray("hisCord");
                        if (hisCord.length() > 0) {
                            list.add(new ReplyDetailsText("处理记录"));
                            List<NoticeItemDetailsRecord> Detailslist = ListJsonUtils.getListByArray(NoticeItemDetailsRecord.class, hisCord.toString());
                            list.addAll(Detailslist);
                        }
                        callBack.onsuccess(list);
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

    /*创建、编辑回复单*/

    /**
     * 整改验证单详情id
     * replyDescription整改描述
     * attachments要上传的附件，和以前那些有上传附件的接口一样
     * deleteFileIds需要删除的附件id，逗号隔开
     */
    public static void editReplyFormDel(Map<String, String> map, ArrayList<File> attachments, final ObjectCallBacks callBacks) {
        PostRequest request = OkGo.post(Requests.EDITREPLYFORMDEL);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            request.params(entry.getKey(), entry.getValue());
        }
        if (attachments.size() > 0) {
            request.addFileParams("attachments", attachments);
        } else {
            request.isMultipart(true);
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        callBacks.onsuccess("成功");
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

    /*获取回复单数据*/
    public static void getReplyFormDel(String replyDelId, final MapCallBack callback) {
        OkGo.get(Requests.GETREPLYFORMDEL)
                .params("replyDelId", replyDelId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                ReplyBillBean bean = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), ReplyBillBean.class);
                                map.put("bean", bean);
                                ArrayList<photoBean> afterFileslist = new ArrayList<>();
                                JSONArray afterFiles;
                                try {
                                    afterFiles = data.getJSONArray("afterFiles");
                                } catch (Exception e) {
                                    afterFiles = new JSONArray();
                                }
                                if (afterFiles.length() > 0) {
                                    for (int i = 0; i < afterFiles.length(); i++) {
                                        JSONObject object = afterFiles.getJSONObject(i);
                                        String path = Requests.networks + object.getString("filepath");
                                        String name = object.getString("filename");
                                        String type = object.getString("id");
                                        afterFileslist.add(new photoBean(path, name, type));
                                    }
                                }
                                map.put("afterFiles", afterFileslist);
                                JSONArray beforeFiles;
                                try {
                                    beforeFiles = data.getJSONArray("beforeFiles");
                                } catch (Exception e) {
                                    beforeFiles = new JSONArray();
                                }
                                ArrayList<photoBean> beforeFileslist = new ArrayList<>();
                                if (beforeFiles.length() > 0) {
                                    for (int i = 0; i < beforeFiles.length(); i++) {
                                        JSONObject object = beforeFiles.getJSONObject(i);
                                        String path = Requests.networks + object.getString("filepath");
                                        String name = object.getString("filename");
                                        String type = object.getString("id");
                                        beforeFileslist.add(new photoBean(path, name, type));
                                    }
                                }
                                map.put("beforeFiles", beforeFileslist);
                                callback.onsuccess(map);
                            } else {
                                callback.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onerror("请求失败");
                    }
                });
    }

    /*提交回复*/
    public static void submitReplyData(String replyId, String motionNode, final ObjectCallBacks callBacks) {
        OkGo.post(Requests.SUBMITREPLYDATA)
                .params("replyId", replyId)
                .params("motionNode", motionNode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBacks.onsuccess("");
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

    /*验证*/

    /**
     * @param id         整改验证单id
     * @param motionNode 运动节点
     * @param isby       否通过，1通过2打回
     */
    public static void getOrgInfoBycnfvalidReply(String id, String motionNode, String verificationOpinion, int isby, final ObjectCallBacks callBacks) {
        OkGo.post(Requests.GETORGINFOBYCNFVALIDREPLY)
                .params("id", id)
                .params("motionNode", motionNode)
                .params("verificationDate", Dates.getDate())
                .params("isby", isby)
                .params("verificationOpinion", verificationOpinion)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callBacks.onsuccess("");
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

    /*删除回复验证单*/
    public static void deleteReplyForm(String id, final ObjectCallBacks callback) {
        OkGo.get(Requests.DELETEREPLYFORM)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                callback.onsuccess("");
                            } else {
                                callback.onerror(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onerror("数据解析失败");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onerror("请求失败");
                    }
                });

    }


    public interface MapCallBack {
        void onsuccess(Map<String, Object> map);

        void onerror(String string);
    }

    public interface ListCallback {
        void onsuccess(ArrayList<Object> list);

        void onerror(String string);
    }

    public interface ObjectCallBacks {
        void onsuccess(String string);

        void onerror(String string);
    }
}
