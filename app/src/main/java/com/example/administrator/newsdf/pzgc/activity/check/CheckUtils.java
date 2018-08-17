package com.example.administrator.newsdf.pzgc.activity.check;

import android.widget.NumberPicker;

import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.pzgc.utils.Utils.month;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class CheckUtils {

    /**
     * /设置选择年，控制二月天数
     */
    public void setyear(NumberPicker monthPicker, NumberPicker dayPicker, int i1, String[] numberyear) {
        //月份
        String mont = month[monthPicker.getValue()];
        //年份
        String str = numberyear[i1];
        //如果选择中的月份是二月
        if (mont.equals("02")) {
            //判断是闰年还是平年
            if (Utils.getyear().contains(str)) {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        }
    }

    /**
     * /设置选择月，控制二月天数
     */
    public void setMonth(NumberPicker yearPicker, NumberPicker monthPicker, NumberPicker dayPicker, int newVal, String[] numbermonth, String[] numberyear) {
        String NewVal = numbermonth[newVal];
        String years = numberyear[yearPicker.getValue()];
        if (NewVal.equals("02")) {
            if (Utils.getyear().contains(years)) {
                //如果是闰年。二月有29天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                //如果是平年。二月有28天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        } else if (NewVal.equals("01") || NewVal.equals("03") || NewVal.equals("05") ||
                NewVal.equals("07") || NewVal.equals("08") || NewVal.equals("10") || NewVal.equals("12")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.day.length - 1);
            dayPicker.setDisplayedValues(Utils.day);
            dayPicker.setMinValue(0);
        } else if (NewVal.equals("04") || NewVal.equals("06") || NewVal.equals("09") || NewVal.equals("11")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.dayth.length - 1);
            dayPicker.setDisplayedValues(Utils.dayth);
            dayPicker.setMinValue(0);
        }
    }


    //类别分类
    public void taskTypeList(String wbsId, final ArrayList<Tenanceview> data, final SettingAdapter adapter) {

        OkGo.post(Requests.TASK_TYPE_LIST)
                .params("wbsId", wbsId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonobject = jsonObject.getJSONObject("data");
                            JSONArray Array = jsonobject.getJSONArray("data");
                            for (int i = 0; i < Array.length(); i++) {
                                JSONObject json = Array.getJSONObject(i);
                                String id = json.getString("id");
                                String name = json.getString("name");
                                String hasChildren = json.getString("hasChildren");
                                data.add(new Tenanceview(id, name, hasChildren));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.getData(data);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showLongToast("请求失败");
                    }

                });

    }

    /**
     * 违反标准
     */
    public void CheckStandardApp(final ArrayList<Audio> mData, final SettingAdapter mAdapter) {
        OkGo.post(Requests.CheckStandardApp)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        LogUtil.d("s", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ToastUtils.showShortToast(jsonObject.getString("msg"));
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String checkType = json.getString("checkType");
                                    String id = json.getString("id");
                                    mData.add(new Audio(checkType, id));
                                }
                            }
                            mAdapter.getData(mData);
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



}