package com.example.administrator.newsdf.pzgc.activity.mine;

import android.os.Bundle;

import com.example.baselibrary.view.BaseActivity;

/**
 * @author lx
 * @data :2019/4/17 0017
 * @描述 : 我的签名
 * @see
 */
public class AutographActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.chad.library.R.layout.quick_view_load_more);
        addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
