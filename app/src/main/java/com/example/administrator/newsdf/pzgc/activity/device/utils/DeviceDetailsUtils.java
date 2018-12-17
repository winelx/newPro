package com.example.administrator.newsdf.pzgc.activity.device.utils;

import com.alibaba.fastjson.JSON;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CorrectReplyBean;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsBean;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsResult;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsTop;
import com.example.administrator.newsdf.pzgc.bean.DeviceReflex;
import com.example.administrator.newsdf.pzgc.bean.DeviceTrem;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.bean.ProblemitemBean;
import com.example.administrator.newsdf.pzgc.bean.SeeDetailsReply;
import com.example.administrator.newsdf.pzgc.bean.SeeDetailsTop;
import com.example.administrator.newsdf.pzgc.bean.VerificationBean;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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
 * @Created by: 2018/12/10 0010.
 * @description:
 * @Activity：
 */

public class DeviceDetailsUtils {
    public interface DeviceDetailslitener {
        /**
         * 接口成功的返回
         *
         * @param lists
         * @param permission
         */
        void onsuccess(ArrayList<Object> lists, ArrayList<Integer> permission);
    }

    public interface SeeDetailslitener {
        /**
         * 接口成功呢的返回
         *
         * @param lists
         */
        void onsuccess(ArrayList<Object> lists);
    }

    /**
     * @param id
     * @ 界面详情
     */
    public void details(String id, final DeviceDetailslitener inface) {
        OkGo.post(Requests.GETSECMAININFO)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Dates.disDialog();
                        ArrayList<Object> lists = new ArrayList<>();
                        ArrayList<Integer> permissionlist = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            //返回数据主体
                            JSONObject data = jsonObject.getJSONObject("data");
                            //整改结果，包含提交、验证结果
                            JSONArray result = data.getJSONArray("result");
                            //权限
                            JSONArray permission = data.getJSONArray("permission");
                            for (int i = 0; i < permission.length(); i++) {
                                Integer integer = (Integer) permission.get(i);
                                permissionlist.add(integer);
                            }
                            //检查标准列表
                            JSONArray standard = data.getJSONArray("standard");
                            DeviceDetailsBean bean = JSON.parseObject(data.toString(), DeviceDetailsBean.class);
                            ArrayList<DeviceTrem> list = new ArrayList<>();
                            list.addAll(ListJsonUtils.getListByArray(DeviceTrem.class, standard.toString()));
                            lists.add(new DeviceDetailsTop(bean, list));
                            for (int i = 0; i < result.length(); i++) {
                                JSONArray jsonArray = result.getJSONArray(i);
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject json = jsonArray.getJSONObject(j);
                                    String operTime = json.getString("operTime");
                                    String realname = json.getString("realname");
                                    String times = json.getString("times");
                                    int type = json.getInt("type");
                                    String business;
                                    try {
                                        business = json.getString("business");
                                    } catch (Exception e) {
                                        business = "";
                                    }
                                    String view;
                                    try {
                                        view = json.getString("view");
                                        ArrayList<FileTypeBean> filselist = new ArrayList<>();
                                        try {
                                            JSONArray file = json.getJSONArray("file");
                                            for (int k = 0; k < file.length(); k++) {
                                                JSONObject josnfile = file.getJSONObject(k);
                                                String filepath = josnfile.getString("filepath");
                                                String fileext = josnfile.getString("fileext");
                                                String name = josnfile.getString("filename");
                                                filselist.add(new FileTypeBean(name, filepath, fileext));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //验证界面，，有文件
                                        lists.add(new DeviceDetailsResult(business, operTime, realname, times, type, view, filselist));
                                    } catch (Exception e) {
                                        //回复
                                        lists.add(new DeviceReflex(business, operTime, realname, times, type));
                                    }

                                }

                            }
                            inface.onsuccess(lists, permissionlist);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    /**
     * 特种设备整改项详细
     *
     * @param id
     * @param networkinterface
     */
    public void problemitemlist(String id, final Networkinterface networkinterface) {
        OkGo.post(Requests.GETSECDETAILSANDREPLY)
                .params("checkId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        ArrayList<CorrectReplyBean> mData = new ArrayList<>();
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject json = data.getJSONObject(i);
                                ProblemitemBean bean = JSON.parseObject(json.toString(), ProblemitemBean.class);
                                //附件
                                JSONArray file = json.getJSONArray("file");
                                ArrayList<FileTypeBean> type = new ArrayList<>();
                                for (int j = 0; j < file.length(); j++) {
                                    JSONObject jsonObject1 = file.getJSONObject(j);
                                    String type1 = jsonObject1.getString("fileext");
                                    String url = Requests.networks + jsonObject1.getString("filepath");
                                    String name = jsonObject1.getString("filename");
                                    type.add(new FileTypeBean(name, url, type1));
                                }
                                //回复附件
                                JSONArray replyFile = json.getJSONArray("replyFile");
                                ArrayList<Audio> audios = new ArrayList<>();
                                for (int k = 0; k < replyFile.length(); k++) {
                                    JSONObject jsonObject2 = replyFile.getJSONObject(k);
                                    String id = jsonObject2.getString("id");
                                    String filepath = Requests.networks + jsonObject2.getString("filepath");
                                    audios.add(new Audio(filepath, id));
                                }
                                mData.add(new CorrectReplyBean(bean, audios, type));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        map.put("data", mData);
                        networkinterface.onsuccess(map);
                    }
                });
    }


    /**
     * 特种设备整改项详细
     *
     * @param id
     * @param networkinterface
     */
    public void seedetails(String id, final SeeDetailslitener networkinterface) {
        OkGo.post(Requests.GETSECDETAILSANDREPLY)
                .params("checkId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Dates.disDialog();
                        ArrayList<Object> mData = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject json = data.getJSONObject(i);
                                String cisName = "违反标准：" + json.getString("cisName");
                                String htlname = "隐患等级：" + json.getString("HTLName");
                                String term = "整改期限：" + json.getString("term");
                                String cause;
                                try {
                                     cause = "整改事由：" + json.getString("cause");
                                } catch (Exception e) {
                                     cause = "整改事由：";
                                }
                                //附件
                                JSONArray file = json.getJSONArray("file");
                                ArrayList<FileTypeBean> type = new ArrayList<>();
                                for (int j = 0; j < file.length(); j++) {
                                    JSONObject jsonObject1 = file.getJSONObject(j);
                                    String type1 = jsonObject1.getString("fileext");
                                    String url = Requests.networks + jsonObject1.getString("filepath");
                                    String name = jsonObject1.getString("filename");
                                    type.add(new FileTypeBean(name, url, type1));
                                }
                                mData.add(new SeeDetailsTop(cisName, htlname, term, cause, type));
                                String reply;
                                try {
                                    reply = "整改描述：" + json.getString("reply");
                                } catch (Exception e) {
                                    reply = "整改描述：";
                                }//回复附件
                                JSONArray replyFile = json.getJSONArray("replyFile");
                                ArrayList<FileTypeBean> audios = new ArrayList<>();
                                for (int k = 0; k < replyFile.length(); k++) {
                                    JSONObject jsonObject1 = replyFile.getJSONObject(k);
                                    String type1 = jsonObject1.getString("fileext");
                                    String url = Requests.networks + jsonObject1.getString("filepath");
                                    String name = jsonObject1.getString("filename");
                                    audios.add(new FileTypeBean(name, url, type1));
                                }
                                mData.add(new SeeDetailsReply(reply, audios));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        networkinterface.onsuccess(mData);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    /**
     * @ 保存修改回复
     * @内容:
     * @author lx
     * @date: 2018/12/14 0014 上午 11:10
     */
    public void savereplyofsec(Map<String, List<File>> file, String reply, String deletelist, final Networkinterface networkinterface) {
        PostRequest getrequest = OkGo.post(Requests.SAVEREPLYOFSEC);
        getrequest.isMultipart(true);
        if (!reply.isEmpty()) {
            getrequest.params("reply-json-str", reply);
        }
        for (Map.Entry<String, List<File>> entry : file.entrySet()) {
            getrequest.addFileParams(entry.getKey(), entry.getValue());
        }
        if (!deletelist.isEmpty()) {
            getrequest.params("deleteFileIds", deletelist);
        }
        getrequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String string, Call call, Response response) {
                Dates.disDialog();
                Map<String, Object> map = new HashMap<>();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        networkinterface.onsuccess(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Dates.disDialog();
            }
        });
    }

    /**
     * 创建、修改验证单
     *
     * @内容:
     * @author lx
     * @date: 2018/12/14 0014 上午 11:34
     */
    public void saveValideByApp(Map<String, String> map, ArrayList<File> files, final Networkinterface networkinface) {
        PostRequest postrequest = OkGo.post(Requests.SAVEVALIDEBYAPP);
        //循环添加
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //将key作为参数，value作为传递的数据
            postrequest.params(entry.getKey(), entry.getValue());
        }
        //判断是否有图p
        if (files.size() > 0) {
            postrequest.addFileParams("imaglist", files);
        } else {
            //强制表单提交
            postrequest.isMultipart(true);
        }
        postrequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String string, Call call, Response response) {
                Dates.disDialog();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        Map<String, Object> map = new HashMap<>();
                        networkinface.onsuccess(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Dates.disDialog();
            }
        });
    }

    /**
     * @内容: 获取通知单
     * @author lx
     * @date: 2018/12/14 0014 下午 2:11
     */
    public void getvalidatedata(String checkId, final Networkinterface networkinterface) {
        OkGo.post(Requests.GETVALIDATEDATA)
                //单据ID
                .params("checkId", checkId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                VerificationBean bean = JSON.parseObject(data.toString(), VerificationBean.class);
                                ArrayList<Audio> file = new ArrayList<>();
                                JSONArray jsonArray = data.getJSONArray("file");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    file.add(new Audio(Requests.networks + json.getString("filepath"), json.getString("id")));
                                }
                                map.put("data", bean);
                                map.put("file", file);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        networkinterface.onsuccess(map);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * @内容: 指派
     * @author lx
     * @date: 2018/12/14 0014 下午 3:48
     */
    public void assignPersonOfSEC(String checkId, String acceptUserId, final Networkinterface networkinterface) {
        OkGo.post(Requests.assignPersonOfSEC)
                .params("checkId", checkId)
                .params("acceptUserId", acceptUserId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            Map<String, Object> map = new HashMap<>();
                            if (ret == 0) {
                                networkinterface.onsuccess(map);
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * @内容: 提交回复
     * @author lx
     * @date: 2018/12/16 0016 下午 1:42
     */
    public void submitreply(String checkId, final Networkinterface networkinterface) {
        OkGo.post(Requests.SUBMITREPLY)
                .params("checkId", checkId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Dates.disDialog();
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                map.put("data", "成功");
                                networkinterface.onsuccess(map);
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });

    }

    /**
     * @内容: 提交验证
     * @author lx
     * @date: 2018/12/16 0016 下午 3:25
     */
    public void submitValide(String checkId, final Networkinterface networkinterface) {
        OkGo.post(Requests.SUBMITVALIDE)
                .params("checkId", checkId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Map<String, Object> map = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                map.put("data", "成功");
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                            networkinterface.onsuccess(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }


}
