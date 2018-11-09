package com.example.zcjlmodule.utils.activity;


import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.bean.StandardDecomposeBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 征拆类型查询
 *
 * @author lx
 * @Created by: 2018/11/8 0008.
 * 所属界面 StandardDecomposeZcActivity
 */
public class StandardUtils {

    /**
     * @param map     数据
     * @param Listter 回调接口
     */
    public void request(Map<String, Object> map, final OnClickListener Listter) {
        int page = (int) map.get("page");
        PostRequest st = OkGo.post(Api.GETLEVYSTANDARDDETAILS)
                .params("orgId", map.get("standardId") + "")
                .params("size",20)
                .params("page",(page - 1));
        //区域不为空
        if (map.get("region") != null) {
            st.params("region", map.get("region") + "");
        }
        //单价不为空
        if (map.get("price") != null && !TextUtils.isEmpty(map.get("price") + "")) {
            st.params("price", map.get("price") + "");
        }
        //征拆类型不为空
        if (map.get("levyType") != null) {
            st.params("levyType", map.get("levyType") + "");
        }
        st.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.i("ss",s);
                List<StandardDecomposeBean> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("ret") == 0) {
                        if (s.contains("data")) {
                            //请求成功
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            list.addAll(ListJsonUtils.getListByArray(StandardDecomposeBean.class, jsonArray.toString()));
                            Listter.onsuccess(list);
                        } else {
                            //请求成功没有数据
                            Listter.onsuccess(list);
                        }
                    } else {
                        //请求失败（可能是参数之类的）
                        Listter.onerror();
                        ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                //网络请求失败
                Listter.onerror();
            }
        });
    }

    public interface OnClickListener {
        void onsuccess(List<StandardDecomposeBean> list);

        void onerror();
    }
}
