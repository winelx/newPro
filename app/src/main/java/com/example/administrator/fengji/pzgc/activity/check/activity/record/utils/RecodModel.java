package com.example.administrator.fengji.pzgc.activity.check.activity.record.utils;

import com.example.administrator.fengji.pzgc.activity.check.activity.record.bean.RecordDetailBean;
import com.example.administrator.fengji.pzgc.activity.check.activity.record.bean.RecordUerListBean;
import com.example.administrator.fengji.pzgc.activity.check.activity.record.bean.RecordlistBean;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;

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

public class RecodModel {

    /**
     * 说明：列表
     * 创建时间： 2020/7/30 0030 10:07
     *
     * @author winelx
     */

    public void getSafetyCheckListByApp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.getHttp(RecordApi.GETSAFETYCHECKLISTBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<RecordlistBean> mData = com.alibaba.fastjson.JSONArray.parseArray(data.toString(), RecordlistBean.class);
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
     * 说明：获取检查记录实体对象
     * 创建时间： 2020/7/30 0030 11:01
     *
     * @author winelx
     */
    public void getspecialcheckrecordbyapp(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.getHttp(RecordApi.GETSPECIALCHECKRECORDBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        if (jsonObject.toString().contains("data")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            RecordDetailBean bean = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), RecordDetailBean.class);
                            adapter.onsuccess(bean);
                        }
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.onerror("解析失败");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                adapter.onerror("请求失败");
            }
        });

    }

    /**
     * 说明：人员选择公共方法
     * 创建时间： 2020/7/30 0030 16:41
     *
     * @author winelx
     */

    public void choosepersionbyapp(Map<String, String> map, NetworkAdapter adapter) {
        NetWork.getHttp(RecordApi.CHOOSEPERSIONBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        if (jsonObject.toString().contains("data")) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<RecordUerListBean> bean = com.alibaba.fastjson.JSONObject.parseArray(data.toString(), RecordUerListBean.class);
                            adapter.onsuccess(bean);
                        }
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.onerror("数据解析失败");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                adapter.onerror("请求失败");
            }
        });
    }

    /**
     * 说明：检查记录删除接口
     * 创建时间： 2020/7/31 0031 13:19
     *
     * @author winelx
     */

    public void deleteSpecialCheckRecordByApp(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.getHttp(RecordApi.DELETESPECIALCHECKRECORDBYAPP, map, new NetWork.networkCallBack() {
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
                    adapter.onerror("数据解析失败");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                adapter.onerror("请求失败");
            }
        });
    }

    public void savespecial(Map<String, String> map, ArrayList<File> files, NetworkAdapter adapter) {
        NetWork.postHttp(RecordApi.SAVESPECIAL, map, files, true, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        if (jsonObject.toString().contains("data")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            RecordDetailBean bean = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), RecordDetailBean.class);
                            adapter.onsuccess(bean);
                        }
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter.onerror("数据解析失败");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                adapter.onerror("请求失败");
            }
        });
    }

    /**
     *
     *说明：提交方法
     *创建时间： 2020/7/31 0031 14:21
     *@author winelx
     */

    public void optionStatusByApp(String id, NetworkAdapter adapter) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.getHttp(RecordApi.OPTIONSTATUSBYAPP, map, new NetWork.networkCallBack() {
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
                    adapter.onerror("数据解析失败");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                adapter.onerror("请求失败");
            }
        });
    }
}
