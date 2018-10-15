package com.example.zcjlmodule.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.zcjlmodule.ui.fragment.MessageFragmentZc;
import com.example.zcjlmodule.ui.fragment.MineFragmentZc;
import com.example.zcjlmodule.ui.fragment.WorkFragmentZc;
import com.example.zcmodule.R;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.bean.BaseTab;

/**
 * description: 消息，工作，我的三个界面的承载界面，使用list来存fragment，减少list的数据就可以删减界面
 * @author lx
 * date: 2018/10/12 0012 上午 8:53
 * update: 2018/10/12 0012
 * version:
*/
public class HomeZcActivity extends BaseActivity {
    private static HomeZcActivity mContext;
    private FragmentTabHost mTabHost;
    private TextView home_img_red;
    private LayoutInflater mInflater;
    private ArrayList<BaseTab> mTabs = new ArrayList<>();
    public static HomeZcActivity getInstance() {
        return mContext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_zc);
        mContext = this;
        //初始化控件
        mTabHost = (FragmentTabHost) findViewById(R.id.mFragmentTabHost);
        initTab();
    }

    //初始化界面
    public void initTab() {
        //添加tab信息，存入集合进行展示
        BaseTab tab_home = new BaseTab(MessageFragmentZc.class, R.string.message, R.drawable.tab_message_style_zc, 0);
        BaseTab tab_mine = new BaseTab(MineFragmentZc.class, R.string.mine, R.drawable.tab_mine_style_zc, 0);
        BaseTab tab_work = new BaseTab(WorkFragmentZc.class, R.string.work, R.drawable.tab_work_style_zc, 0);
        mTabs.add(tab_home);
        mTabs.add(tab_work);
        mTabs.add(tab_mine);
        mTabHost = (FragmentTabHost) findViewById(R.id.mFragmentTabHost);
        for (BaseTab tab : mTabs) {
            //获取文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragemnt(), null);
        }
        //设置分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        //设置默认打开的界面
        mTabHost.setCurrentTab(0);

    }

    private View buildIndicator(BaseTab tab) {
        mInflater = LayoutInflater.from(this);
        //必须实现setup不然没法展示， getSupportFragmentManager用来装布局的
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //mInflater记得初始化，不然会报空指针，没注意，被坑
        View view = mInflater.inflate(R.layout.tab_index_zc, null);
        //获取控件ID
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textview = (TextView) view.findViewById(R.id.text);
        //d动态添加图片文字，类似listview 的adapter的getItem，
        imageView.setBackgroundResource(tab.getIcon());
        textview.setText(tab.getTitle());
        return view;
    }
}
