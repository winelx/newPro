package com.example.administrator.newsdf.pzgc.activity.check;

import android.widget.NumberPicker;

import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;
import com.example.administrator.newsdf.pzgc.bean.standarBean;
import com.example.baselibrary.inface.NetworkCallback;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.utils.network.NetWork;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        if ("02".equals(mont)) {
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
        if ("02".equals(NewVal)) {
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
        } else if ("01".equals(NewVal) || "03".equals(NewVal) || "05".equals(NewVal) ||
                "07".equals(NewVal) || "08".equals(NewVal) || "10".equals(NewVal) || "12".equals(NewVal)) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.day.length - 1);
            dayPicker.setDisplayedValues(Utils.day);
            dayPicker.setMinValue(0);
        } else if ("04".equals(NewVal) || "06".equals(NewVal) || "09".equals(NewVal) || "11".equals(NewVal)) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.dayth.length - 1);
            dayPicker.setDisplayedValues(Utils.dayth);
            dayPicker.setMinValue(0);
        }
    }


    //类别分类
    public void taskTypeList(String wbsId, final ArrayList<Tenanceview> data, String type, final SettingAdapter adapter) {
        OkGo.post(Requests.TASK_TYPE_LIST)
                .params("Id", wbsId)
                .params("type", type)
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

    /*检查项列表*/
    public void getcheckitemlist(String id, String sysMsgNoticeId, final NetworkCallback callback) {
        PostRequest str = OkGo.post(Requests.SIMPLE_DETAILS_LIST_BY_APP);
        str.params("id", id);
        if (sysMsgNoticeId != null) {
            str.params("sysMsgNoticeId", sysMsgNoticeId);
        }
        str.tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Map<String, Object> map = new HashMap<>();
                                ArrayList<chekitemList> list = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String score = json.getString("score");
                                        String sequence = json.getString("sequence");
                                        String standardScore;
                                        try {
                                            standardScore = json.getString("standardScore");
                                        } catch (JSONException e) {
                                            standardScore = "";
                                        }
                                        boolean noSuch = json.getBoolean("noSuch");
                                        boolean gray = json.getBoolean("gray");
                                        boolean penalty = json.getBoolean("penalty");
                                        boolean generate;
                                        try {
                                            generate = json.getBoolean("generate");
                                        } catch (JSONException e) {
                                            generate = false;
                                        }
                                        int number = i + 1;
                                        String s_type;
                                        try {
                                            s_type = json.getString("stype");
                                        } catch (Exception e) {
                                            s_type = "";
                                        }
                                        //将组织所属公司添加到集合
                                        list.add(new chekitemList(id, score, sequence, number + "", standardScore, s_type, i + 1, generate, noSuch, penalty, gray));
                                    }
                                    map.put("list", list);
                                    callback.onsuccess(map);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onerror(Enums.ANALYSIS_ERROR);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onerror(Enums.REQUEST_ERROR);
                    }
                });
    }


    /**
     * 违反标准
     */
    public void CheckStandardApp(String id, final Onclick onclick) {
        final ArrayList<standarBean> List = new ArrayList<>();
        if (id.length() > 0) {
            OkGo.post(Requests.GET_CHECK_STANDARD_DEAL_APP)
                    .params("checkStandardId", id)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        int number = i + 1;
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String standardDel;
                                        try {
                                            standardDel = json.getString("standardDel");
                                        } catch (Exception e) {
                                            standardDel = "";
                                        }
                                        String standardDelCode;
                                        try {
                                            standardDelCode = json.getString("standardDelCode");
                                        } catch (Exception e) {
                                            standardDelCode = "";
                                        }
                                        String standardDelName;
                                        try {
                                            standardDelName = number + "、" + json.getString("standardDelName");
                                        } catch (Exception e) {
                                            standardDelName = "";
                                        }
                                        String standardDelScore;
                                        try {
                                            standardDelScore = json.getString("standardDelScore");
                                        } catch (Exception e) {
                                            standardDelScore = "";
                                        }
                                        String standardType;
                                        try {
                                            standardType = json.getString("standardType");
                                        } catch (Exception e) {
                                            standardType = "";
                                        }
                                        String standardTypeName;
                                        try {
                                            standardTypeName = json.getString("standardTypeName");
                                        } catch (Exception e) {
                                            standardTypeName = "";
                                        }
                                        List.add(new standarBean(standardDel, standardDelCode, standardDelName, standardDelScore, standardType, standardTypeName));
                                    }
                                } else {
                                    ToastUtils.showShortToast(jsonObject.getString("msg"));
                                }
                                onclick.onSuccess(List);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.showShortToast("数据解析出问题");
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ToastUtils.showShortToast("请求失败");
                        }
                    });
        }
    }

    public void getdatecon(final ArrayList<Audio> mData, final SettingAdapter mAdapter) {
        OkGo.post(Requests.CheckStandardApp)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String checkType = json.getString("checkType");
                                    String id = json.getString("id");
                                    mData.add(new Audio(checkType, id));
                                }
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
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

    //确认并签字
    public static void getautograph(String id, final NetworkCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetWork.getHttp(Requests.CONFIRM, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("ret") == 0) {
                        callback.onsuccess(new HashMap<String, Object>());
                    } else if (jsonObject.getInt("ret") == 5) {
                        callback.onerror(Enums.MYAUTOGRAPH);
                    } else {
                        callback.onerror(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onerror(Enums.ANALYSIS_ERROR);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                callback.onerror(Enums.REQUEST_ERROR);
            }
        });
    }

    public interface Onclick {
        void onSuccess(ArrayList<standarBean> List);
    }


    public static void getCategory(String id, String sysMsgNoticeId, final NetworkCallback callback) {
        PostRequest str = OkGo.post(Requests.CHECKGET_BY_ID);
        str.params("Id", id);
        str.params("sysMsgNoticeId", sysMsgNoticeId);
        str.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        Map<String, Object> map = new HashMap<>();
                        JSONObject json = jsonObject.getJSONObject("data");
                        //具体时间
                        map.put("checkDate", json.getString("checkDate"));
                        //检查标准类别
                        map.put("wbsTaskTypeName", json.getString("wbsTaskTypeName"));
                        //检查组织
                        map.put("checkOrgName", json.getString("checkOrgName"));
                        map.put("status", json.getInt("status"));
                        //检查部位wbs
                        map.put("wbsMainName", json.getString("wbsMainName"));
                        //判断内业还是外业
                        map.put("iwork", json.getString("iwork"));
                        map.put("wbsMainName", json.getString("wbsMainName"));
                        //检查人
                        map.put("realname", json.getString("realname"));
                        //检查标题
                        map.put("name", json.getString("name"));
                        //所属标段
                        map.put("orgName", json.getString("orgName"));
                        //检查部位
                        map.put("partDetails", json.getString("partDetails"));
                        String score;
                        try {
                            score = json.getString("score");
                            if (score.equals("0.0")) {
                                map.put("score", "0");
                            } else {
                                map.put("score", score);
                            }
                        } catch (JSONException e) {
                            score = "";
                        }
                        map.put("id", json.getString("id"));
                        callback.onsuccess(map);
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callback.onerror(Enums.REQUEST_ERROR);
            }
        });
    }


}
