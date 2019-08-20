package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerlistActivity;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.LoedgerAllListbean;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.LoedgerMineListbean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseViewModel;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;

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
 * @Author lx
 * @创建时间 2019/7/31 0031 13:46
 * @说明 专项施工台账列表 model类
 * @see LoedgerlistActivity
 **/

public class LoedgerlistModel extends BaseViewModel {
    private MutableLiveData<List<Object>> data;
    private List<Object> list;

    public MutableLiveData<List<Object>> getAllData(String choice, String orgid, int page) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (page == 1) {
            list.clear();
        }
        allrequest(choice, orgid, page);
        return data;
    }

    public void allrequest(String choice, String orgid, int page) {
        if (page == 1) {
            list.clear();
        }
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgid);
        map.put("page", page + "");
        map.put("rows", 15 + "");
        //如果为空，就不传递到后台
        if (!choice.isEmpty()) {
            map.put("isDeal", choice);
        }
        NetWork.postHttp(Api.SPECIAL_ITEM_MAIN_LIST, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("results");
                    List<LoedgerAllListbean> datas = new ArrayList<>();
                    datas = com.alibaba.fastjson.JSONObject.parseArray(result.toString(), LoedgerAllListbean.class);
                    list.addAll(datas);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                data.setValue(list);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
                modelinface.onerror();
            }
        });
    }


    /**
     * 我的列表显示
     *
     * @param choice 赛选条件
     * @param orgid  组织id
     * @param page   页数
     * @return
     */
    public MutableLiveData<List<Object>> getMyData(String choice, String orgid, int page) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (page==1){
            list.clear();
        }
        myrequest(choice, orgid, page);
        return data;
    }

    public void myrequest(String choice, String orgid, int page) {
        if (page == 1) {
            list.clear();
        }
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgid);
        map.put("page", page + "");
        map.put("rows", 15 + "");
        //如果为空，就不传递到后台
        if (!choice.isEmpty()) {
            map.put("isDeal", choice);
        }
        NetWork.postHttp(Api.MY_SPECIAL_ITEM_MAIN_LIST, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("results");
                    List<LoedgerMineListbean> datas = new ArrayList<>();
                    datas = com.alibaba.fastjson.JSONObject.parseArray(result.toString(), LoedgerMineListbean.class);
                    list.addAll(datas);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data.setValue(list);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
                modelinface.onerror();
            }
        });
    }

}
