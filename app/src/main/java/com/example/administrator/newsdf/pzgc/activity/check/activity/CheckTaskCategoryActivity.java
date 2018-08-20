package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.CategoryContent;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.Categorylist;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PshooseFragAdapte;
import com.example.administrator.newsdf.pzgc.utils.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * description:检查标准
 *
 * @author lx
 *         date: 2018/8/6 0006 上午 8:55
 *         update: 2018/8/6 0006
 *         version:
 */
public class CheckTaskCategoryActivity extends AppCompatActivity {
    private NoScrollViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task_category);

        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        //图册
        fragments.add(new Categorylist());
        //标准
        fragments.add(new CategoryContent());
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

    public void dismiss() {
        int item = viewpager.getCurrentItem();
        if (item == 1) {
            viewpager.setCurrentItem(0);
        } else {
            finish();
        }
    }

    public void result(String str,String id) {
        Intent intent = new Intent();
        //回传数据到主Activity
        intent.putExtra("data", str);
        intent.putExtra("id", id);
        setResult(2, intent);
        finish();
    }
}
