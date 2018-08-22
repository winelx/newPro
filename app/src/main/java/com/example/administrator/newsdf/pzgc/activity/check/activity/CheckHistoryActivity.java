package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/22 0022.
 */

public class CheckHistoryActivity extends AppCompatActivity {
    private SettingAdapter adapter;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> msg = new ArrayList<>();
    ArrayList<Audio> mdata = new ArrayList<>();
    private ListView wbs_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);
        wbs_listview = (ListView) findViewById(R.id.wbs_listview);
        Intent intent = getIntent();
        msg = intent.getStringArrayListExtra("msg");
        data = intent.getStringArrayListExtra("data");
        for (int i = 0; i < msg.size(); i++) {
            mdata.add(new Audio(msg.get(i), data.get(i)));
        }
        adapter = new SettingAdapter<Audio>(mdata, R.layout.taskrecord_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(R.id.task_cord_data, obj.getName());
                String data = obj.getContent();
                data = data.substring(0, 10);
                holder.setText(R.id.task_cord, data);
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
