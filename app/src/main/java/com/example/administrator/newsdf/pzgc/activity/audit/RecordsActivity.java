package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.Recordsbean;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:
 *
 * @author lx
 *         date: 2018/7/16 0016 上午 11:01
 *         update: 2018/7/16 0016
 *         version:
 */

public class RecordsActivity extends AppCompatActivity {
    private String taskid;
    private SettingAdapter adapter;
    private List<Recordsbean> mData;
    private Context mContext;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_record);
        ListView list = (ListView) findViewById(R.id.task_list);
        mContext = this;
        mData = new ArrayList<>();
        Intent intent = getIntent();
        taskid = intent.getExtras().getString("taskid");
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new SettingAdapter<Recordsbean>(mData, R.layout.activity_records_item) {
            @Override
            public void bindView(ViewHolder holder, Recordsbean obj) {
                holder.setText(R.id.create_time, obj.getCreatedata());
                holder.setText(R.id.create_user, obj.getRealname());
                holder.setText(R.id.create_jobs, obj.getJobs());
                String pass = obj.getPass();
                if (pass.equals("1")) {
                    holder.setText(R.id.create_status, mContext, "通过", R.color.finish_green);
                } else {
                    holder.setText(R.id.create_status, mContext, "打回", R.color.red);
                }
            }
        };
        list.setAdapter(adapter);
        getData();
    }

    public void getData() {
        OkGo.post(Requests.Auditrecords)
                .params("id", taskid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject json = new JSONObject(s);
                            JSONArray array = json.getJSONArray("data");
                            int ret = json.getInt("ret");
                            if (ret == 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsons = array.getJSONObject(i);
                                    String jobs = jsons.getString("jobs");
                                    String create_date = jsons.getString("create_date");
                                    String realname = jsons.getString("realname");
                                    String pass = jsons.getString("pass");
                                    mData.add(new Recordsbean(create_date, realname, jobs, pass));
                                }
                                adapter.getData(mData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
