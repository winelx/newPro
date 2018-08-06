package com.example.administrator.newsdf.pzgc.activity.check;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.DKDragView;

public class CheckitemActivity extends AppCompatActivity {
    private DKDragView dkDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkitem);
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                ToastUtils.showShortToast("ss");
            }
        });
    }
}
