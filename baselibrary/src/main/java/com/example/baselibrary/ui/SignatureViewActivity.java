package com.example.baselibrary.ui;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.example.baselibrary.R;
import com.example.baselibrary.view.signature.SignatureView;

import java.io.IOException;


public class SignatureViewActivity extends AppCompatActivity {
    private SignatureView signatureView;
    private ImageView image;
    private String paths;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatureview);
        //保存路径及保存文件名
        paths = getExternalCacheDir().getPath() + "/signa/image.png";
        image = findViewById(R.id.image);
        image.setVisibility(View.GONE);
        signatureView = findViewById(R.id.signatureview);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //path：保存的地址；clearBlank：是否清除空白区域；blank：空白区域留空距离；
                try {
                    signatureView.save(paths, false, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
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
