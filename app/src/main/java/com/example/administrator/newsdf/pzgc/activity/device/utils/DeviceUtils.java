package com.example.administrator.newsdf.pzgc.activity.device.utils;

import android.view.View;

import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.treeviews.SimpleTreeListViewAdapters;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
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
        void onsuccess(ArrayList<MyNoticeDataBean> data);
    }

    public interface ViolickLitener {
        void onsussess(List<OrgBeans> mDatas2,
                       List<OrgenBeans> data);
    }

    public interface ViolickLitenerlist {
        void onsuccess(ArrayList<String> data);
    }



    private boolean status = true;

    /**
     * 特种设备全部界面网络请求
     *
     * @param onclickLitener
     */
    public void deviceall(final AllOnclickLitener onclickLitener) {
        final ArrayList<String> list = new ArrayList<>();
        final Map<String, List<Home_item>> ListMap = new HashMap<>();
        list.add("测试数据1");
        list.add("测试数据2");
        list.add("测试数据3");
        list.add("测试数据4");
        ArrayList<Home_item> demo1 = new ArrayList<>();
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        ListMap.put("测试数据1", demo1);
        ListMap.put("测试数据2", demo1);
        ListMap.put("测试数据3", demo1);
        ListMap.put("测试数据4", demo1);
        onclickLitener.onsuccess(list, ListMap);
//        OkGo.post("")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        onclickLitener.onsuccess(list, ListMap);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });
    }

    /**
     * 特种设备我的界面网络请求
     *
     * @param onclickLitener
     */
    public void deviceme(final MeOnclickLitener onclickLitener) {
        final ArrayList<Home_item> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                    "组织", "1", "false", "1", "1212", false));
        }
        onclickLitener.onsuccess(list);
//        OkGo.post("")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        onclickLitener.onsuccess(list);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//
//                    }
//                });
    }

    /**
     * 特种设备我的整改消息列表网络请求
     */
    public void decicemessagelist(final MeListOnclickLitener onclickLitener) {
        ArrayList<MyNoticeDataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyNoticeDataBean("测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", false));
            list.add(new MyNoticeDataBean("测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", true));
        }
//        OkGo.post("")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
////                        onclickLitener.onsuccess(list);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });
        onclickLitener.onsuccess(list);
    }

    /**
     * 违反标准tree
     */
    public void violateetree(final ViolickLitener violickLitener) {
        final List<OrgBeans> mDatas2 = new ArrayList<>();
        final List<OrgenBeans> mData = new ArrayList<>();
        OkGo.<String>post(Requests.Swatchmakeup)
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
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                    JSONObject json = jsonObject1.getJSONObject("organization");
                                    String Id;
                                    try {
                                        Id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Id = "";
                                    }
                                    String type;
                                    try {
                                        type = json.getString("orgType");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        type = "";
                                    }
                                    String parentId;
                                    try {
                                        parentId = json.getString("parentId");
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
        ArrayList<String> list = new ArrayList<>();
        litenerlist.onsuccess(list);
//        OkGo.get("")
//                .params("", id)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        ArrayList<String> list = new ArrayList<>();
//                        litenerlist.onsuccess(list);
//                    }
        //              });
    }


}