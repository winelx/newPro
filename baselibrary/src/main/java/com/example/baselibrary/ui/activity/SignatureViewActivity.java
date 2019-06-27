package com.example.baselibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.baselibrary.R;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.ui.fragment.AutographDraw;
import com.example.baselibrary.ui.fragment.AutographPreview;
import com.example.baselibrary.view.NoScrollViewPager;
import com.example.baselibrary.view.signature.SignatureView;


import java.util.ArrayList;
import java.util.List;

/**
 *@author： lx
 *@创建时间： 2019/6/19 0019 11:29
 *@说明：
 **/
public class SignatureViewActivity extends AppCompatActivity {
    private NoScrollViewPager viewPager;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatureview);
        viewPager = findViewById(R.id.noscroll);
        mContext = this;
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new AutographPreview());
        fragments.add(new AutographDraw());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        //设定适配器
        viewPager.setAdapter(adapter);
    }

    public void setItem(int page) {
        viewPager.setCurrentItem(page);
    }

    public void setvertical() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void sethorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void backactivity() {
        this.finish();
    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int item = viewPager.getCurrentItem();
            if (item == 1) {
                setvertical();
                viewPager.setCurrentItem(0);

            } else {
                finish();
            }
            return true;
        }
        return true;
    }


}