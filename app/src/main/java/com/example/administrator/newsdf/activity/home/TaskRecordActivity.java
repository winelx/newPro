package com.example.administrator.newsdf.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.adapter.SettingAdapter;
import com.example.administrator.newsdf.bean.Tenanceview;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Request;
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
public class TaskRecordActivity extends AppCompatActivity {
    private TextView com_title;
    private IconTextView com_back;
    private SettingAdapter mAdapter;
    private ListView task_list;
    private ArrayList<Tenanceview> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_record);
        com_title = (TextView) findViewById(R.id.com_title);
        com_back = (IconTextView) findViewById(R.id.com_back);
        task_list = (ListView) findViewById(R.id.task_list);
        mData = new ArrayList<>();
        com_title.setText("操作记录");
        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");
        OkGo.<String>post(Request.TASKRECORD)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtils.showShortToast(s);

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
                                    json.getString("name");
                                }
                                ToastUtils.showShortToast("请求数据成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //listview的适配器
        mAdapter = new SettingAdapter<Tenanceview>(mData, R.layout.taskrecord_item) {
            @Override
            public void bindView(ViewHolder holder, Tenanceview obj) {
                holder.setText(R.id.task_cord_name, obj.getName());
                holder.setText(R.id.task_cord_number, obj.getUnmber());
                holder.setText(R.id.task_cord_time, obj.getId());
            }
        };
        task_list.setAdapter(mAdapter);
    }
}
