package com.example.zcjlmodule.presenter;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.model.NewAddOriginalModel;
import com.example.zcjlmodule.utils.Api;
import com.example.zcjlmodule.view.NewAddOriginalView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import measure.jjxx.com.baselibrary.base.BasePresenters;
import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/2 0002.
 * @description:
 */

public class NewAddOriginalPresenter extends BasePresenters<NewAddOriginalView> {
    private NewAddOriginalModel.Model model;
    private Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,4})?$");
    /**
     * 保存数据
     */
    public void save(Map<String, String> map, ArrayList<String> path) {

        model = new NewAddOriginalModel.NewAddOriginalModelIpm();
        ArrayList<File> file = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            file.add(new File(path.get(i)));
        }
        model.getData(map, file, new NewAddOriginalModel.Model.OnClickListener() {
            @Override
            public void onComple() {
                mView.OnSuccess();
            }

            @Override
            public void onError() {
                mView.OnError();
            }
        });
    }

    /**
     * 显示隐藏图标
     */
    public void visibility(boolean lean, ArrayList<ImageView> imagelist) {
        if (lean) {
            for (int i = 0; i < imagelist.size(); i++) {
                imagelist.get(i).setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < imagelist.size(); i++) {
                imagelist.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 输入框点击事件屏蔽
     */
    public void clickable(boolean lean, ArrayList<EditText> editTexlist) {
        if (lean) {
            for (int i = 0; i < editTexlist.size(); i++) {
                editTexlist.get(i).setEnabled(true);
            }
        } else {
            for (int i = 0; i < editTexlist.size(); i++) {
                editTexlist.get(i).setEnabled(false);
            }
        }
    }

    /**
     * 界面控件的margin的设置
     *
     * @param v 控件
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    public void validateHouseholder(String orgId, String cardId, String householder, final OnClickListener listener) {
        OkGo.get(Api.VALIDATEHOUSEHOLDER)
                .params("orgId", orgId)
                .params("idCard", cardId)
                .params("householder", householder)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                listener.onsuccess(true);
                            } else {
                                listener.onsuccess(false);
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
     * 接口
     */
    public interface OnClickListener {
        void onsuccess(boolean lean);
    }

    //金额验证
    public boolean isNumber(String str) {
        // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (!match.matches()) {
            return false;
        } else {
            return true;
        }
    }

}
