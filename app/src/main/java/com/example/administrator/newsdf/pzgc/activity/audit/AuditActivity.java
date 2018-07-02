package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Auditbean;
import com.example.administrator.newsdf.pzgc.bean.Audittitlebean;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 审核列表界面
 *
 * @author lx
 *         date: 2018/7/2 0002 下午 1:47
 *         update: 2018/7/2 0002
 *         version:
 */

public class AuditActivity extends AppCompatActivity {
    private Context mContext;
    private List<Auditbean> mData;
    private ArrayList<Audittitlebean> title;
    private ListView aduit_list;
    private SettingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        mContext = AuditActivity.this;
        title = new ArrayList<>();
        title.add(new Audittitlebean("2018年07月01日", "20", "审核"));
        title.add(new Audittitlebean("2018年07月01日", "32", "未审核"));
        title.add(new Audittitlebean("2018年07月01日", "15", "审核"));
        title.add(new Audittitlebean("2018年07月01日", "64", "审核"));
        title.add(new Audittitlebean("2018年07月01日", "85", "未审核"));
        aduit_list = (ListView) findViewById(R.id.aduit_list);
        adapter = new SettingAdapter<Audittitlebean>(title, R.layout.item_audit_elv) {
            @Override
            public void bindView(ViewHolder holder, Audittitlebean obj) {
                holder.setText(R.id.todaytime, obj.getTitle());
                holder.setText(R.id.complete, "完成率:"+obj.getComplete());
                holder.setText(R.id.unfinished, "未审核:"+obj.getUnfinished());
            }
        };
        aduit_list.setAdapter(adapter);
        aduit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showLongToast(position + "");
            }
        });
    }

}
