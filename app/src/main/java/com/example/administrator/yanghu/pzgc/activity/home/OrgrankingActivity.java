package com.example.administrator.yanghu.pzgc.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.check.activity.CheckReportActivity;
import com.example.baselibrary.base.BaseActivity;

/**
 * @Author lx
 * @创建时间 2019/7/4 0004 15:55
 * @说明 标段排名选择界面（月度，季度）
 **/

public class OrgrankingActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView com_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgranking);
        mContext = this;
        com_title=findViewById(R.id.com_title);
        com_title.setText("标段排名");
        findViewById(R.id.lin_monthly).setOnClickListener(this);
        findViewById(R.id.lin_quarter).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_monthly:
                //月份
                Intent intents= new Intent(mContext, CheckReportActivity.class);
                intents.putExtra("type","month");
                startActivity(intents);
                break;
            case R.id.lin_quarter:
                //季度
                Intent intent= new Intent(mContext, CheckReportActivity.class);
                intent.putExtra("type","report");
                startActivity(intent);
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;

        }
    }
}
