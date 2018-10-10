package com.example.zcjlmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zcmodule.R;

import measure.jjxx.com.baselibrary.utils.ToastUtlis;

public class ModuleMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_main);
        ToastUtlis.getInstance().showLongToast("ssss");
    }
}
