package com.example.administrator.newsdf.pzgc.activity.work;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.BrightViewpagerAdapter;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;

import static com.example.administrator.newsdf.R.id.bridht_company;
import static com.example.administrator.newsdf.R.id.bridht_group;
import static com.example.administrator.newsdf.R.id.bridht_project;


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
    private static BrightspotActivity mContext;

    public static BrightspotActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bright);
        mContext = this;
        //集团
        bridhtGroup = (TextView) findViewById(bridht_group);
        //公司
        bridhtCompany = (TextView) findViewById(bridht_company);
        //项目
        bridhtProject = (TextView) findViewById(bridht_project);
        bridhtViewpager = (ViewPager) findViewById(R.id.bridht_viewpager);
        findViewById(R.id.bridht_back).setOnClickListener(this);
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
        viewList.add("集团");
        viewList.add("公司");
        viewList.add("项目");
        mAdapter = new BrightViewpagerAdapter(getSupportFragmentManager(), viewList);
        //缓存3个界面
        bridhtViewpager.setOffscreenPageLimit(3);
        bridhtViewpager.setAdapter(mAdapter);
        bridhtViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > currentPosition) {
                    //右滑
                    currentPosition = position;
                } else if (position < currentPosition) {
                    //左滑
                    currentPosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        groupselect();
                        break;
                    case 1:
                        companyselect();
                        break;
                    case 2:
                        projecrtselect();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case bridht_group:
                //集团
                bridhtViewpager.setCurrentItem(0);
                groupselect();
                break;
            case bridht_company:
                bridhtViewpager.setCurrentItem(1);
                companyselect();
                //公司
                break;
            case bridht_project:
                bridhtViewpager.setCurrentItem(2);
                projecrtselect();
                //项目
                break;
            case R.id.bridht_back:
                finish();
            default:
                break;
        }
    }

    //集团
    public void groupselect() {
        bridhtGroup.setTextColor(Color.parseColor("#F27F19"));
        bridhtCompany.setTextColor(Color.parseColor("#646464"));
        bridhtProject.setTextColor(Color.parseColor("#646464"));
    }

    //分公司
    public void companyselect() {
        bridhtGroup.setTextColor(Color.parseColor("#646464"));
        bridhtCompany.setTextColor(Color.parseColor("#F27F19"));
        bridhtProject.setTextColor(Color.parseColor("#646464"));
    }

    //项目
    public void projecrtselect() {
        bridhtGroup.setTextColor(Color.parseColor("#646464"));
        bridhtCompany.setTextColor(Color.parseColor("#646464"));
        bridhtProject.setTextColor(Color.parseColor("#F27F19"));
    }


}
