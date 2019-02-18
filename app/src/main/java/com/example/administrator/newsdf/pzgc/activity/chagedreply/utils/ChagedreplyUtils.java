package com.example.administrator.newsdf.pzgc.activity.chagedreply.utils;

import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ChagedreplyList;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.RelationList;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

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
    public static void getReplyFormOfSaveStatus() {
        OkGo.post(Requests.GETREPLYFORMOFSAVESTATUS)
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

    /*创建整改验证单*/
    public static void createReplyForm() {
        OkGo.post(Requests.CREATEREPLYFORM)
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

    /*创建整改验证单时选择整改通知单*/
    public static void getNoticeFormList(String orgId, int page, final MapCallBack listCallback) {
        OkGo.get(Requests.GETNOTICEFORMLIST)
                .params("orgId", orgId)
                .params("size", 20)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            Map<String,Object> map = new HashMap<>();
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray result = data.getJSONArray("results");
                                List<RelationList> list = ListJsonUtils.getListByArray(RelationList.class, result.toString());
                                map.put("Relation",list);
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
    public static void chooseNoticeDelData() {
        OkGo.post(Requests.CHOOSENOTICEDELDATA)
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

    /*保存导入问题项*/
    public static void batchSaveReplyDel() {
        OkGo.post(Requests.BATCHSAVEREPLYDEL)
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

    /*获取整改验证单数据详情*/
    public static void getReplyDelData() {
        OkGo.post(Requests.GETREPLYDELDATA)
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

    /*删除整改验证单详情*/
    public static void deleteReplyDel() {
        OkGo.post(Requests.DELETEREPLYDEL)
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

    /*获取非保存状态的回复验证单主要信息*/
    public static void getReplyFormMainInfo() {
        OkGo.post(Requests.GETREPLYFORMMAININFO)
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

    /*创建、编辑回复单*/
    public static void editReplyFormDel() {
        OkGo.post(Requests.EDITREPLYFORMDEL)
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

    /*获取回复单数据*/
    public static void getReplyFormDel() {
        OkGo.post(Requests.GETREPLYFORMDEL)
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

    /*提交回复*/
    public static void submitReplyData() {
        OkGo.post(Requests.SUBMITREPLYDATA)
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

    /*验证*/
    public static void getOrgInfoBycnfvalidReply() {
        OkGo.post(Requests.GETORGINFOBYCNFVALIDREPLY)
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

    public interface MapCallBack {
        void onsuccess(Map<String, Object> map);

        void onerror(String str);
    }

    public interface ListCallback {
        void onsuccess(ArrayList<Object> list);

        void onerror(String str);
    }

    public interface ObjectCallBacks {
        void onsuccess(String string);

        void onerror(String string);
    }
}
