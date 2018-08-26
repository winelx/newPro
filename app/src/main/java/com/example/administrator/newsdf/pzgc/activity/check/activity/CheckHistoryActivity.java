package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;

import java.util.ArrayList;

import static com.example.administrator.newsdf.R.id.task_cord;

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
        TextView com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("c处理记录");
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
                holder.setSize(R.id.task_cord_data, 10);

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
