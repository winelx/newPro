package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.base.BaseActivity;


/**
 * @Author lx
 * @创建时间 2019/8/2 0002 15:15
 * @说明 赛选审核人
 **/

public class ProgrammeAuditorActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        findViewById(R.id.com_back).setOnClickListener(this);
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
