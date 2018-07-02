package com.example.administrator.newsdf.zlaq.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Tab;
import com.example.administrator.newsdf.zlaq.fragment.UnifiedCenterFragment;
import com.example.administrator.newsdf.zlaq.fragment.UnifiedMessageFragment;
import com.example.administrator.newsdf.zlaq.fragment.UnifiedMineFragment;

import java.util.ArrayList;

/**
 * description: 统一登录平台首页三个fragment的承载界面
 *
 * @author lx
 *         date: 2018/7/2 0002 上午 10:54
 *         update: 2018/7/2 0002
 *         version:
 */
public class UnifiedHomeActivity extends AppCompatActivity {
    private ArrayList<Tab> mTabs = new ArrayList<>();
    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_home);
//        StatusBarUtil.setTranslucent(UnifiedHomeActivity.this, 0);
        //添加tab信息，存入集合进行展示
        Tab tab_home = new Tab(UnifiedMessageFragment.class, R.string.message, R.drawable.tab_home_style, 0);
        Tab tab_work = new Tab(UnifiedCenterFragment.class, R.string.application, R.drawable.unfile_center_style, 0);
        Tab tab_hot = new Tab(UnifiedMineFragment.class, R.string.mine, R.drawable.tab_mine_style, 0);
        mTabs.add(tab_home);
        mTabs.add(tab_work);
        mTabs.add(tab_hot);

        mTabHost = (FragmentTabHost) findViewById(R.id.mFragmentTabHost);
        for (Tab tab : mTabs) {
            //获取都文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragemnt(), null);
        }
        //设置分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        //设置默认打开的界面
        mTabHost.setCurrentTab(1);
    }

    private View buildIndicator(Tab tab) {
        mInflater = LayoutInflater.from(this);
        //必须实现setup不然没法展示， getSupportFragmentManager用来装布局的
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //mInflater记得初始化，不然会报空指针，没注意，被坑
        View view = mInflater.inflate(R.layout.tab_index, null);
        //获取控件ID
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textview = (TextView) view.findViewById(R.id.text);
        //d动态添加图片文字，类似listview 的adapter的getItem，
        imageView.setBackgroundResource(tab.getIcon());
        textview.setText(tab.getTitle());
        return view;
    }

}
