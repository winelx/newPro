package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils;


import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckitemActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckDetailBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ExternalCheckListBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ProcesshiscordBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.SafetyCheck;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.view.BaseDialog;
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
                        map.put("company", company);
                        CheckDetailBean.Group group = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), CheckDetailBean.Group.class);
                        map.put("group", group);
                        adapter.onsuccess(map);
                    } else {
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

    /**
     * 说明：细项保存接口
     * 创建时间： 2020/7/6 0006 14:32
     *
     * @author winelx
     */
    public void saveSafetyCheckDelByApp(Map<String, String> map, ArrayList<File> files, NetworkAdapter adapter) {
        PostRequest post = OkGo.post(ExternalApi.SAVESAFETYCHECKDELBYAPP);
        //如果为true，进行表单提交
        post.isMultipart(true);
        //是否传递文件
        if (files != null) {
            post.addFileParams("imagelist", files);
        }
        //传递参数
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                post.params(entry.getKey(), entry.getValue());

            }
        }
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        adapter.onsuccess("");
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                        adapter.onerror();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtils.showShortToast("请求失败");
                adapter.onerror();
            }
        });

    }

    /**
     * 说明：外业检查流程记录
     * 创建时间： 2020/7/7 0007 13:42
     *
     * @author winelx
     */
    public void getprocesshiscord(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("modelId", id);
        NetWork.postHttp(ExternalApi.GETPROCESSHISCORD, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<ProcesshiscordBean> list = com.alibaba.fastjson.JSONArray.parseArray(data.toString(), ProcesshiscordBean.class);
                        adapter.onsuccess(list);
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("网络请求失败");
            }
        });
    }

    /**
     * 说明：外业检查 提交、确认方法
     * 创建时间： 2020/7/7 0007 16:54
     *
     * @author winelx
     */
    public void submitdatabyapp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.postHttp(ExternalApi.SUBMITDATABYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        adapter.onsuccess();
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("网络请求失败");
            }
        });
    }

    /**
     * 说明：删除外业数据接口
     * 创建时间： 2020/7/10 0010 13:32
     *
     * @author winelx
     */
    public void deletesafetycheckbyapp(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.postHttp(ExternalApi.DELETESAFETYCHECKBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        adapter.onsuccess();
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
     * 说明：
     * 创建时间： 2020/7/13 0013 9:55
     *
     * @author winelx
     */
    public void returnsafetycheckbyapp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.postHttp(ExternalApi.RETURNSAFETYCHECKBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        adapter.onsuccess();
                    } else {
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
}
