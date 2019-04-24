package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.CheckstandardContent;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.Checkstandarditem;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * description:违反标准
 *
 * @author lx
 * date: 2018/8/9 0009 上午 11:48
 * update: 2018/8/9 0009
 * version:
 */

public class CheckstandardListActivity extends BaseActivity {
    private NoScrollViewPager viewpager;
    private String name, strid;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task_category);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new CheckstandardContent());
        fragments.add(new Checkstandarditem());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        //设定适配器
        viewpager = (NoScrollViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(adapter);

    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int item = viewpager.getCurrentItem();
            if (item == 1) {
                viewpager.setCurrentItem(0);
            } else {
                finish();
            }
            return true;
        }
        return true;
    }

    public void setItem() {
        viewpager.setCurrentItem(1);
    }

    public void getdata(String str, String id) {
        this.name = str;
        this.strid = id;
    }

    public void dismiss() {
        int item = viewpager.getCurrentItem();
        if (item == 1) {
            viewpager.setCurrentItem(0);
        } else {
            finish();
        }
    }

    public void result(String str, String id, String score, String code) {
        if (title != null) {
            Intent intent = new Intent();
            //回传数据到主Activity
            intent.putExtra("content", str);
            intent.putExtra("id", id);
            intent.putExtra("datastr", name);
            intent.putExtra("dataid", strid);
            intent.putExtra("score", score);
            intent.putExtra("stancode", code);
            setResult(2, intent);
            finish();
        }
    }

}
