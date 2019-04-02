package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.DeviceRecordBean;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/12/17 0017.
 * @description:
 * @Activity： 处理记录
 */

public class DeviceRecordActivity extends BaseActivity {
    private String checkId;
    private ListView listview;
    private SettingAdapter adapter;
    private ArrayList<DeviceRecordBean> list;
    private Context mContext;
    private LinearLayout nullposion;
    private TextView com_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);
        addActivity(this);
        mContext = this;
        list = new ArrayList<>();
        Intent intent = getIntent();
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("处理记录");
        checkId = intent.getStringExtra("id");
        nullposion = (LinearLayout) findViewById(R.id.nullposion);
        listview = (ListView) findViewById(R.id.wbs_listview);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new SettingAdapter<DeviceRecordBean>(list, R.layout.taskrecord_item) {
            @Override
            public void bindView(ViewHolder holder, DeviceRecordBean obj) {
                holder.setText(R.id.task_cord_data, obj.getDate());
                if (obj.getO() != null) {
                    holder.setText(R.id.task_cord, obj.getO());
                }
                //  类型2指派 5提交回复 11项目经理验证打回 12项目经理验证通过 22下发人验证打回 23下发人验证通过
                int type = obj.getType();
                SpannableString str = null;
                switch (type) {
                    case 2:
                        str = Dates.setText(mContext, obj.getS() + "：" + obj.getV(), obj.getS().length() + 1, R.color.Orange);
                        holder.setText(R.id.task_cord_name, str);
                        break;
                    case 5:
                        str = Dates.setText(mContext, obj.getS() + "：" + obj.getV(), obj.getS().length() + 1, R.color.Orange);
                        holder.setText(R.id.task_cord_name, str);
                        break;
                    case 11:
                        str = Dates.setText(mContext, obj.getS() + "：" + obj.getV(), obj.getS().length() + 1, R.color.red);
                        holder.setText(R.id.task_cord_name, str);
                        break;
                    case 12:
                        str = Dates.setText(mContext, obj.getS() + "：" + obj.getV(), obj.getS().length() + 1, R.color.finish_green);
                        holder.setText(R.id.task_cord_name, str);
                        break;
                    case 22:
                        str = Dates.setText(mContext, obj.getS() + "：" + obj.getV(), obj.getS().length() + 1, R.color.red);
                        holder.setText(R.id.task_cord_name, str);
                        break;
                    case 23:
                        str = Dates.setText(mContext, obj.getS() + "：" + obj.getV(), obj.getS().length() + 1, R.color.finish_green);
                        holder.setText(R.id.task_cord_name, str);
                        break;
                    default:
                        break;
                }

            }
        };
        listview.setAdapter(adapter);
        request();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    public void request() {
        OkGo.post(Requests.GETOPERHIS)
                .params("checkId", checkId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = -jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                list.addAll(ListJsonUtils.getListByArray(DeviceRecordBean.class, jsonArray.toString()));
                                adapter.getData(list);
                                if (list.size() > 0) {
                                    nullposion.setVisibility(View.GONE);
                                } else {
                                    nullposion.setVisibility(View.VISIBLE);
                                }
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
}
