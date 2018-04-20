package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.BrightViewpagerAdapter;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;


/**
 * description: 亮点展示
 *
 * @author lx
 *         date: 2018/4/19 0019 下午 4:21
 *         update: 2018/4/19 0019
 *         version:
 */

public class BrightspotActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager bridhtViewpager;
    private TextView bridhtGroup, bridhtCompany, bridhtProject;
    private BrightViewpagerAdapter mAdapter;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bright);
        mContext = BrightspotActivity.this;
        //集团
        bridhtGroup = (TextView) findViewById(R.id.bridht_group);
        //公司
        bridhtCompany = (TextView) findViewById(R.id.bridht_company);
        //项目
        bridhtProject = (TextView) findViewById(R.id.bridht_project);
        bridhtViewpager = (ViewPager) findViewById(R.id.bridht_viewpager);
        bridhtGroup.setOnClickListener(this);
        bridhtCompany.setOnClickListener(this);
        bridhtProject.setOnClickListener(this);
        //处理数据
        initData();
    }

    private void initData() {
        //定义一个视图集合（用来装左右滑动的页面视图）
        ArrayList<String> viewList = new ArrayList<String>();
        //定义两个视图，两个视图都加载同一个布局文件list_view.ml
//        View Group = getLayoutInflater().inflate(R.layout.bright_list_view, null);
        viewList.add("集团");
        viewList.add("公司");
        viewList.add("项目");
        mAdapter = new BrightViewpagerAdapter(getSupportFragmentManager(), viewList);
        bridhtViewpager.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bridht_group:
                //集团
                break;
            case R.id.bridht_company:
                //公司
                break;
            case R.id.bridht_project:
                //项目
                break;
            default:
                break;
        }
    }
}
