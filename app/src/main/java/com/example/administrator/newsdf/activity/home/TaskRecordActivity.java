package com.example.administrator.newsdf.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Request;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_record);
        com_title = (TextView) findViewById(R.id.com_title);
        com_back = (IconTextView) findViewById(R.id.com_back);
        com_title.setText("操作记录");
        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");
        OkGo.<String>post(Request.TASKRECORD)
                .params("taskId", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtils.showShortToast(s);
                        Log.i("taskId", s);
                    }
                });
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
