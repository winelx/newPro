package com.example.administrator.newsdf.pzgc.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.AppUtils;
import com.example.baselibrary.view.BaseActivity;

/**
 * @author lx
 *         这关于我们的界面
 */
public class AboutmeActivity extends BaseActivity {
    String version;
    private TextView versions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        versions = (TextView) findViewById(R.id.text_version);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        version = AppUtils.getVersionName(AboutmeActivity.this);
        versions.setText(version);
    }
}
