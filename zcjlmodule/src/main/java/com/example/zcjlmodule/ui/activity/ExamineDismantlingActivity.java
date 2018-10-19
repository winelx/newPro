package com.example.zcjlmodule.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zcmodule.R;

/**
 * description:查看征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 4:50
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面StandardDecomposeZcActivity
 */
public class ExamineDismantlingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_dismantling);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title= (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("查看征拆标准");
    }
}
