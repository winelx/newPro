package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ImportChageditemAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.baselibrary.EmptyUtils;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：导入整改项
 * {@link }
 */
public class ChagedImportitemActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ImportChageditemAdapter adapter;
    private RecyclerView recyclerList;
    private ArrayList<String> list;
    private EmptyUtils emptyUtils;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_importchageditem);
        mContext = this;
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.com_title);
        title.setText("导入问题项");
        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        for (int i = 0; i < 10; i++) {
            list.add("1212");
        }
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImportChageditemAdapter(R.layout.adapter_item_improtchageditem, list);
        adapter.setEmptyView(emptyUtils.init());
        recyclerList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, CheckitemActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
