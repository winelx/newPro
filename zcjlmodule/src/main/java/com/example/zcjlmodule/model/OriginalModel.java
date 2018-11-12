package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ListJsonUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public class OriginalModel {


    public interface OnClicklister {
        void onSuccess(ArrayList<OriginalZcBean> list);

        void onError();
    }

    public interface Model extends BaseView {
        void getdata(String orgid, int page, Map<String, String> map, OriginalModel.OnClicklister onClicklister);
    }


    public static class OriginalModelPml implements Model {
        @Override
        public void getdata(String orgId, int page, Map<String, String> map, final OnClicklister listener) {
            final ArrayList<OriginalZcBean> list = new ArrayList<>();
            GetRequest getrequest = OkGo.get(Api.GETBUSRAWVALUATIONS)
                    .params("orgId", orgId)
                    .params("page", page)
                    .params("size", 20);

            //如果page ==1，就计算总金额
            if (page==1){
                getrequest.params("isCount",true);
            }
            //如果map大于0，就将map传递（map的筛选类型）也坑可能没有值所以进行空异常处理
            try {
                if (map.size()>0){
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        getrequest.params(entry.getKey(), entry.getValue());
                    }
                }
            }catch (NullPointerException e){

            }

            getrequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        int ret = jsonObject.getInt("ret");
                        if (ret == 0) {
                            if (s.contains("data")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                list.addAll(ListJsonUtils.getListByArray(OriginalZcBean.class, jsonArray.toString()));
                            }
                        } else {
                            ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                        }
                        listener.onSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {

                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    listener.onError();
                }
            });


        }
    }

}
