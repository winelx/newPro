package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.utils.activity.MeasureUtils;

import java.util.List;


/**
 * description: 征拆类型查询
 *
 * @author lx
 *         date: 2018/10/18 0018 下午 4:07
 *         update: 2018/10/18 0018
 *         version:
 *         跳转界面 StandardDecomposeZcActivity
 *         这个是一个listview 写的tree界面
 */
public class DismantlingtypequeryZcActivity extends AppCompatActivity {
    private ListView mListView;
    private MeasureUtils utils;
    private String ogrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = new MeasureUtils();
        Intent intent = getIntent();
        ogrId = intent.getStringExtra("orgId");
        setContentView(R.layout.activity_dismantlingtypequery_zc);
        mListView = (ListView) findViewById(R.id.dis_typequery_list);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("征拆类型查询");
        httprequest();
    }

    private void httprequest() {
        utils.collectiontype(ogrId, new MeasureUtils.TypeOnClickListener() {

            @Override
            public void onsuccess(List<OrgenBeans> mData) {

            }

            @Override
            public void onerror() {

            }
        });
    }
}
