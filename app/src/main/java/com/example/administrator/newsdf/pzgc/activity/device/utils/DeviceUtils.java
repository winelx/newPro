package com.example.administrator.newsdf.pzgc.activity.device.utils;


import com.alibaba.fastjson.JSON;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.DetailsBean;
import com.example.administrator.newsdf.pzgc.bean.DeviceMeList;
import com.example.administrator.newsdf.pzgc.bean.Devicedetails;
import com.example.administrator.newsdf.pzgc.bean.HiddendangerBean;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.bean.ProblemBean;
import com.example.administrator.newsdf.pzgc.bean.ProblemFile;
import com.example.administrator.newsdf.pzgc.bean.SecstandardlistBean;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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
 * @Created by: 2018/11/27 0027.
 * @description: 特种设备网络请求帮助类
 */

public class DeviceUtils {
    public interface AllOnclickLitener {
        void onsuccess(ArrayList<String> list, Map<String, List<Home_item>> getListMap);
    }

    public interface MeOnclickLitener {
        void onsuccess(ArrayList<Home_item> list);
    }

    public interface MeListOnclickLitener {
        void onsuccess(ArrayList<DeviceMeList> data);
    }

    public interface ViolickLitener {
        void onsussess(List<OrgBeans> mDatas2,
                       List<OrgenBeans> data);
    }

    public interface ViolickLitenerlist {
        void onsuccess(ArrayList<SecstandardlistBean> data);
    }

    public interface FacilityckLitenerlist {
        void onsuccess(ArrayList<Audio> data);
    }

    public interface hiddenLitenerList {
        void onsuccess(ArrayList<HiddendangerBean> data);
    }

    public interface devicesavelitener {
        void success(String number, String id);
    }

    public interface MainInfolitener {
        void success(Devicedetails bean, ArrayList<DetailsBean> list);
    }

    public interface ProblemLitener {
        void success(ProblemBean bean, ArrayList<ProblemFile> list);
    }

    private boolean status = true;

    /**
     * 特种设备全部界面网络请求
     *
     * @param onclickLitener
     */
    public void deviceall(final AllOnclickLitener onclickLitener) {
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<Home_item> mData = new ArrayList<>();
        final Map<String, List<Home_item>> map = new HashMap<>();

        OkGo.post(Requests.GETORGINFO)
                .params("isAll", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String count = json.getString("count");
                                    String id = json.getString("id");
                                    String name = json.getString("name");
//                                    String org_type = json.getString("org_type");
                                    //父级
                                    String parentId = json.getString("parentId");
                                    String parentName = json.getString("parentName");
                                    //将组织所属公司添加到集合
                                    if (!list.contains(parentName)) {
                                        list.add(parentName);
                                    }
                                    mData.add(new Home_item("", "", id, "", name, count, "", parentName, parentId, false));
                                }
                                //是否有数据
                                if (mData.size() != 0) {
                                    for (String str : list) {
                                        List<Home_item> list = new ArrayList<Home_item>();
                                        for (Home_item item : mData) {
                                            String name = item.getParentname();
                                            if (str.equals(name)) {
                                                list.add(item);
                                                map.put(str, list);
                                            }
                                        }
                                    }
                                }
                                onclickLitener.onsuccess(list, map);
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
     * 特种设备我的界面网络请求
     *
     * @param onclickLitener
     */
    public void deviceme(final MeOnclickLitener onclickLitener) {
        final ArrayList<Home_item> mData = new ArrayList<>();
        OkGo.get(Requests.GETORGINFO)
                .params("isAll", false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                mData.clear();
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String id = jsonObject1.getString("id");
                                        String name = jsonObject1.getString("name");
                                        String orgtype = jsonObject1.getString("count");
                                        String parentId = jsonObject1.getString("parentId");
                                        String parentName = jsonObject1.getString("parentName");
                                        mData.add(new Home_item("", "", id, "", name, orgtype, "", parentName, parentId, false));
                                    }
                                }
                                onclickLitener.onsuccess(mData);
                            }
                        } catch (Exception e) {
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
     * 特种设备我的整改消息列表网络请求
     */
    public void decicemessagelist(boolean isAll, String orgId, final int page, int status, final MeListOnclickLitener onclickLitener) {
        final ArrayList<DeviceMeList> list = new ArrayList<>();
        PostRequest postrequest = OkGo.post(Requests.GETSECHECKLIST)
                .params("isAll", isAll)
                .params("orgId", orgId)
                .params("page", page)
                .params("size", 15);
        //如果等于-1，就是查询全部，不传status
        if (status != -1) {
            postrequest.params("status", status);
        }
        postrequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Dates.disDialog();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    JSONObject data = jsonObject.getJSONObject("data");
                    int totalPages = data.getInt("totalPages");
                    if (ret == 0) {
                        JSONArray jsonArray = data.getJSONArray("results");
                        list.addAll(ListJsonUtils.getListByArray(DeviceMeList.class, jsonArray.toString()));
//                        if (page > totalPages) {
//                            ToastUtils.showLongToast("所有数据展示完毕");
//                        }
                    } else {
                        ToastUtils.showLongToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onclickLitener.onsuccess(list);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Dates.disDialog();
            }
        });
    }

    /**
     * 违反标准tree
     */
    public void violateetree(String typeId, final ViolickLitener violickLitener) {
        final List<OrgBeans> mDatas2 = new ArrayList<>();
        final List<OrgenBeans> mData = new ArrayList<>();
        OkGo.<String>post(Requests.STANDARDTREE)
                .params("typeId", typeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDatas2.clear();
                        mData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
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
                                        type = json.getString("type");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        type = "";
                                    }
                                    String parentId;
                                    try {
                                        parentId = json.getString("pId");
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
                                    violickLitener.onsussess(mDatas2, mData);
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
                                            violickLitener.onsussess(mDatas2, mData);
                                        }
                                    }
                                }
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * @description: 违反标准列表
     * @author lx
     * @date: 2018/12/3 0003 下午 3:07
     */
    public void violateelist(String id, final ViolickLitenerlist litenerlist) {
        final ArrayList<SecstandardlistBean> list = new ArrayList<>();
        OkGo.get(Requests.SECSTANDARDLIST)
                .params("qdId", id)
                .params("page", 1)
                .params("size", 300)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("results");
                            list.addAll(ListJsonUtils.getListByArray(SecstandardlistBean.class, jsonArray.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        litenerlist.onsuccess(list);
                    }
                });
    }

    /**
     * @内容:特种设备名称
     * @author lx
     * @date: 2018/12/10 0010 下午 2:18
     */
    public void facilityname(final FacilityckLitenerlist facilityckLitenerlist) {
        OkGo.get(Requests.SETASKTYPESELECTLIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ArrayList<Audio> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String name = json.getString("name");
                                String id = json.getString("id");
                                list.add(new Audio(name, id));
                            }
                            facilityckLitenerlist.onsuccess(list);
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
     * 隐患等级
     */
    public void hiddendangergrade(final hiddenLitenerList litenerList) {
        OkGo.get(Requests.HIDDENTROUBLELEVEL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        ArrayList<HiddendangerBean> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            list.addAll(ListJsonUtils.getListByArray(HiddendangerBean.class, jsonArray.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        litenerList.onsuccess(list);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 特种设备整改单保存
     */
    public void devicesave(Map<String, Object> map, final devicesavelitener litener) {
        PostRequest postrequest = OkGo.post(Requests.DEVICESAVESEC);
        //遍历map中的值
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            postrequest.params(entry.getKey(), entry.getValue() + "");
        }
        postrequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String string, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String numbers = data.getString("numbers");
                    String id = data.getString("id");
                    litener.success(numbers, id);
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
     * 特种设备检查主要数据
     */
    public void getSECMainInfo(String id, final MainInfolitener infolitener) {
        OkGo.post(Requests.GETMAININFOBYEDIT)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject json = jsonObject.getJSONObject("data");
                                JSONArray details = json.getJSONArray("details");
                                ArrayList<DetailsBean> list = new ArrayList<>();
                                Devicedetails textBean = JSON.parseObject(json.toString(), Devicedetails.class);
                                list.addAll(ListJsonUtils.getListByArray(DetailsBean.class, details.toString()));
                                infolitener.success(textBean, list);
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
                    }
                });
    }

    /**
     * 特种设备删除单据
     *
     * @param id
     * @param liter
     */
    public void devicedelete(String id, final devicesavelitener liter) {
        OkGo.post(Requests.devicedelete)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                liter.success("", "");
                            } else {
                                ToastUtils.showLongToast("删除失败");
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
     * @内容: 保存整改通知问题项
     * @author lx
     * @date: 2018/12/12 0012 上午 10:37
     */
    public void saveSECDetails(Map<String, Object> map, ArrayList<File> file, final devicesavelitener tener) {
        PostRequest postrequest = OkGo.post(Requests.SAVESECDETAILS);
        //遍历map中的值
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            postrequest.params(entry.getKey(), entry.getValue().toString());
        }
        if (file.size() > 0) {
            postrequest.addFileParams("imagesList", file);
        } else {
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
                        tener.success("", "");
                    }else {
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
     * @内容: 下发整改通知
     * @author lx
     * @date: 2018/12/12 0012 上午 10:37
     */
    public void sendseccheck(String id, final devicesavelitener litener) {
        OkGo.get(Requests.SENDSECCHECK)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                litener.success("", "");
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
                        int code = response.networkResponse().code();
                        LogUtil.i("network", code);
                    }
                });
    }

    /**
     * @内容:
     * @author 特种设备整改项详情
     * @date: 2018/12/12 0012 下午 1:43
     */
    public void secdetailsbyedit(String id, final ProblemLitener litener) {
        OkGo.post(Requests.GETSECDETAILSBYEDIT)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        ArrayList<ProblemFile> list = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("file");
                            ProblemBean bean = JSON.parseObject(data.toString(), ProblemBean.class);
                            if (jsonArray.length() > 0) {
                                list.addAll(ListJsonUtils.getListByArray(ProblemFile.class, jsonArray.toString()));
                            }
                            litener.success(bean, list);
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
     * @内容: 特种设备提交验证
     * @author lx
     * @date: 2018/12/14 0014 上午 11:03
     */
    public void submitValide(String id, final Networkinterface networkinterface) {
        OkGo.get(Requests.SUBMITVALIDE)
                .params("checkId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            Map<String, Object> map = new HashMap<>();
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
                });
    }

    /**
     * @内容: 删除检查项
     * @author lx
     * @date: 2018/12/16 0016 下午 2:32
     */
    public void deleteitem(String id, final Networkinterface networkinterface) {
        OkGo.get(Requests.DELETESECDETAILSBYID)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Dates.disDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            Map<String, Object> map = new HashMap<>();
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
}