package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.baselibrary.view.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/10 0010.
 * @description: 特种设备名称
 * @Activity：
 */

public class FacilitynameActivity extends BaseActivity {
    private ListView gradeRecy;
    private SettingAdapter adapter;
    private ArrayList<Audio> list;
    private Context mContext;
    private DeviceUtils deviceUtils;
    private LinearLayout home_backgroud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationa);
        addActivity(this);
        mContext = this;
        deviceUtils = new DeviceUtils();
        home_backgroud = (LinearLayout) findViewById(R.id.home_backgroud);
        TextView titleView = (TextView) findViewById(R.id.com_title);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setText("特种设备名称");
        list = new ArrayList<>();
        gradeRecy = (ListView) findViewById(R.id.organ_list_item);
        adapter = new SettingAdapter<Audio>(list, R.layout.task_category_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(R.id.category_content, obj.getName());
            }
        };
        gradeRecy.setAdapter(adapter);
        deviceUtils.facilityname(new DeviceUtils.FacilityckLitenerlist() {
            @Override
            public void onsuccess(ArrayList<Audio> data) {
                list.addAll(data);
                adapter.getData(list);
            }
        });
        gradeRecy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("id", list.get(position).getContent());
                //设置返回数据
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

}
