package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.SdDismantlingBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class SdDismantlingModel {
    public interface OnClickListener {
        void onSuccess(ArrayList<SdDismantlingBean> list);

        void onError();
    }

    public interface Model extends BaseView {
        void init(String orgId, int page, OnClickListener onClickListener);
    }

    public static class SdDismantlingModelIpm implements Model {
        @Override
        public void init(String orgId, int page, final OnClickListener onClickListener) {
            final ArrayList<SdDismantlingBean> list = new ArrayList();
            OkGo.get(Api.GETLEVYSTANDARD)
                    .params("orgId", orgId)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    JSONArray jsonArray =jsonObject.getJSONArray("data");
                                    for (int i = 0; i < ; i++) {
                                        JSONObject json =jsonArray.getJSONObject(i);
                                        String number=json.getString("number");
                                        String name=json.getString("name");
                                        String fileName=json.getString("fileName");
                                        String fileNumber=json.getString("fileNumber");
                                        String createDate=json.getString("createDate");
                                        String provinceName =json.getString("provinceName");
                                        String cityName =json.getString("cityName");
                                        String countyName =json.getString("countyName");
                                    }
                                } else {
                                    ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            onClickListener.onSuccess(list);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            onClickListener.onError();
                        }
                    });
        }
    }
}
