package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.MoretasklistBean;
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
 * Created by Administrator on
 */

/**
 * description: 检查模块——下发通知的选择联系人
 *
 * @author lx
 *         date: 2018/8/8 0008 上午 9:37
 *         update: 2018/8/7 0007
 *         version:
 */

public class CheckuserActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView expandableListView;

    private TextView comtitle;
    private Context mContext;
    private ArrayList<MoretasklistBean> list;
    private SettingAdapter mAdapter;
    String orgId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbs);
        mContext = CheckuserActivity.this;
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        expandableListView = (ListView) findViewById(R.id.wbs_listview);
        comtitle = (TextView) findViewById(R.id.com_title);
        comtitle.setText("选择责任人");
        findViewById(R.id.com_back).setOnClickListener(this);
        mAdapter = new SettingAdapter<MoretasklistBean>(list, R.layout.checkuser_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, final MoretasklistBean obj) {
                holder.setText(R.id.content_name, obj.getPartContent());
                holder.setText(R.id.content_zhiw, obj.getUploadTime());
                holder.setOnClickListener(R.id.check_user, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newpush = new Intent();
                        newpush.putExtra("name", obj.getPartContent());
                        newpush.putExtra("userId", obj.getId());
                        //回传数据到主Activity
                        setResult(2, newpush);
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        expandableListView.setAdapter(mAdapter);

        OkGo.post(Requests.GET_PERSON_DATA_APP)
                .params("orgId", orgId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                list = new ArrayList<MoretasklistBean>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        String position = json.getString("position");
                                        list.add(new MoretasklistBean(position, name, id));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            default:
                break;
        }
    }

    public void getdata(String name) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        setResult(3, intent);
        finish();
    }


}
