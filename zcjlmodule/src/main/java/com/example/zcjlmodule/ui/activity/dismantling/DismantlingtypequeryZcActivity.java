package com.example.zcjlmodule.ui.activity.dismantling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.zcjlmodule.R;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismantlingtypequery_zc);
        mListView = (ListView) findViewById(R.id.dis_typequery_list);
    }
}
