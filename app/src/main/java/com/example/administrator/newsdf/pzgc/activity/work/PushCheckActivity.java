package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.base.BaseActivity;

/**
 * description: 任务下发
 *
 * @author lx
 *         date: 2018/3/26 0026 上午 9:48
 *         update: 2018/3/26 0026
 *         version:
 */
public class PushCheckActivity extends BaseActivity {
    private RelativeLayout pchoose_atlas, pchoose_wbs;
    private Context mContext;
    TextView com_titlle;
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_check);
        mContext = PushCheckActivity.this;
        com_titlle = (TextView) findViewById(R.id.com_title);
        com_titlle.setText("任务下发");
        findViewById(R.id.pchoose_atlas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MmissPushActivity.class);
                intent.putExtra("Type", "details");
                intent.putExtra("title", "任务推送");
                startActivity(intent);
            }
        });
        findViewById(R.id.pchoose_wbs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MmissPushActivity.class);
                intent.putExtra("Type", "push");
                intent.putExtra("title", "任务推送");
                startActivity(intent);
            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
