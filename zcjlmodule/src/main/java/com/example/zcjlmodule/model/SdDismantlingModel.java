package com.example.zcjlmodule.model;

import com.example.zcjlmodule.bean.SdDismantlingBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:获取征拆标准
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
                    .params("page", page)
                    .params("size", 20)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        //编号
                                        String number;
                                        try {
                                            //title
                                            number = json.getString("name");
                                        } catch (Exception e) {
                                            number = "";
                                        }
                                        //id
                                        String id;
                                        try {
                                            id = json.getString("id");
                                        } catch (Exception e) {
                                            id = "";
                                        }
                                        //文件名称
                                        String fileName;
                                        try {
                                            fileName = json.getString("fileName");
                                        } catch (Exception e) {
                                            fileName = "";
                                        }
                                        //文件编号
                                        String fileNumber;
                                        try {
                                            fileNumber = json.getString("fileNumber");
                                        } catch (Exception e) {
                                            fileNumber = "";
                                        }
                                        //创建时间
                                        String createDate;
                                        try {
                                            createDate = json.getString("releaseDate").substring(0, 10);
                                        } catch (Exception e) {
                                            createDate = "";
                                        }
                                        //省
                                        String provinceName;
                                        try {
                                            provinceName = json.getString("provinceName");
                                        } catch (Exception e) {
                                            provinceName = "";
                                        }
                                        //城市
                                        String cityName;
                                        try {
                                            cityName = json.getString("cityName");
                                        } catch (Exception e) {
                                            cityName = "";
                                        }
                                        //区域
                                        String countyName;
                                        try {
                                            countyName = json.getString("countyName");
                                        } catch (Exception e) {
                                            countyName = "";
                                        }
                                        //乡镇
                                        String townName;
                                        try {
                                            townName = json.getString("townName");
                                        } catch (Exception e) {
                                            townName = "";
                                        }
                                        //备注
                                        String remarks;
                                        try {
                                            remarks = json.getString("remarks");
                                        } catch (Exception e) {
                                            remarks = "";
                                        }
                                        //发布人
                                        String releasor;
                                        try {
                                            releasor = json.getString("releasor");
                                        } catch (Exception e) {
                                            releasor = "";
                                        }
                                        String cityNames = provinceName + cityName + countyName;
                                        list.add(new SdDismantlingBean(id, number, fileNumber, fileName, cityNames, createDate,
                                                provinceName, cityName, countyName, townName, remarks, releasor));
                                    }
                                } else {
                                    ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                                }
                            } catch (Exception e) {
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
