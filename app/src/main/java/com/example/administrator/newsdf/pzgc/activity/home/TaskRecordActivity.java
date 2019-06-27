package com.example.administrator.newsdf.pzgc.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.TaskRecordAdapter;
import com.example.administrator.newsdf.pzgc.bean.Tenanceview;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.Requests;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 任务详情记录
 *
 * @author lx
 *         date: 2018/3/21 0021 下午 5:14
 *         update: 2018/3/21 0021
 *         version:
 */
public class TaskRecordActivity extends BaseActivity {
    private TextView com_title;
    private IconTextView com_back;
    private TaskRecordAdapter mAdapter;
    private ListView task_list;
    private ArrayList<Tenanceview> mData;
    private String taskId;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_record);

        mContext = TaskRecordActivity.this;
        com_title = (TextView) findViewById(R.id.com_title);
        com_back = (IconTextView) findViewById(R.id.com_back);
        task_list = (ListView) findViewById(R.id.task_list);
        mData = new ArrayList<>();
        com_title.setText("查看记录");
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        LogUtil.i("ss",taskId);
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //listview的适配器
        mAdapter = new TaskRecordAdapter(mContext);
        task_list.setAdapter(mAdapter);
        okGo();

    }

    public void okGo() {
        OkGo.<String>post(Requests.TASKRECORD)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
                                    String name;
                                    try {
                                        name = json.getString("browserName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        name = "";
                                    }
                                    String times;
                                    try {
                                        times = json.getString("times");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        times = "";
                                    }
                                    String wbsId;
                                    try {
                                        wbsId = json.getString("updateDate");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        wbsId = json.getString("createDate");
                                    }
                                    mData.add(new Tenanceview(wbsId, name, times));
                                }
                                mAdapter.getData(mData);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
