package com.example.administrator.fengji.pzgc.activity.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.adapter.SettingAdapter;
import com.example.administrator.fengji.pzgc.bean.Audio;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;

import static com.example.administrator.fengji.R.id.task_cord;

/**
 * Created by Administrator on 2018/8/22 0022.
 * 审核通知单记录
 */

public class CheckHistoryActivity extends BaseActivity {
    private SettingAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> msg = new ArrayList<>();
    private   ArrayList<Audio> mdata = new ArrayList<>();
    private ListView wbs_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);
        wbs_listview = (ListView) findViewById(R.id.wbs_listview);
        TextView com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("处理记录");
        Intent intent = getIntent();
        msg = intent.getStringArrayListExtra("msg");
        data = intent.getStringArrayListExtra("data");
        for (int i = 0; i < msg.size(); i++) {
            mdata.add(new Audio(msg.get(i), data.get(i)));
        }
        adapter = new SettingAdapter<Audio>(mdata, R.layout.taskrecord_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(task_cord, obj.getName());
                String data = obj.getContent();
                holder.setText(R.id.task_cord_data, data);
                holder.setSize(R.id.task_cord_data, 12);
            }
        };
        wbs_listview.setAdapter(adapter);
        wbs_listview.setEmptyView(findViewById(R.id.nullposion));
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
