package com.example.zcjlmodule.model;

import android.util.Log;

import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.LogUtil;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/7 0007.
 * @description:
 */

public class NewAddOriginalModel {
    public interface Model extends BaseView {
        /**
         * 获取数据的方法
         *
         * @param map             数据集合
         * @param onClickListener
         */
        void getData(Map<String, String> map, ArrayList<File> files, NewAddOriginalModel.Model.OnClickListener onClickListener);

        /**
         * 接口
         */
        interface OnClickListener {
            void onComple(String str,String number);

            void onError();
        }
    }

    public static class NewAddOriginalModelIpm implements NewAddOriginalModel.Model {
        @Override
        public void getData(Map<String, String> map, ArrayList<File> files, final OnClickListener onClickListener) {
            final JSONObject json = new JSONObject(map);
            PostRequest request = OkGo.post(Api.SAVERAW);
            //遍历map中的值
            for (Map.Entry<String, String> entry : map.entrySet()) {
                request.params(entry.getKey(), entry.getValue());
            }
            if (files.size() > 0) {
                request.addFileParams("images", files);
            }
            request.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        LogUtil.i("勘丈表", s);
                        JSONObject jsonObject = new JSONObject(s);
                        int ret = jsonObject.getInt("ret");
                        if (ret == 0) {
                            //保存成功返回单据Id
                            //将保存成功的返回的id传递传递给activity的id,（新的单据如果不返回id,请求图片时就id就为空）
                            JSONObject data = jsonObject.getJSONObject("data");
                            String id = data.getString("id");
                            String number = data.getString("number");
                            onClickListener.onComple(id,number);
                        } else {
                            ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                            onClickListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
