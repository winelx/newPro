package com.example.zcjlmodule.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.FmPagerAdapter;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseFragment;
import measure.jjxx.com.baselibrary.utils.LogUtil;

/**
 * description: 征拆首页的消息界面
 * 使用recyclerview的展示控件，根据点击的position判断点击的项，
 * （承载界面HomeZcActivity）
 *
 * @author lx
 *         2018/10/10 0010 下午 2:54
 */
public class MessageFragmentZc extends BaseFragment implements View.OnClickListener {
    //界面控件
    private View rootView;
    //上下文
    private Context mContext;
    private TextView toolbarTitle, fragmentessageagency, fragmentmessagehavedone;
    private RelativeLayout fragmentMessagLin;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_zc, null);
            init();
            initpage();
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    /**
     * 初始化数据
     */
    @SuppressLint("WrongViewCast")
    private void init() {
        mContext = getActivity();
        toolbarTitle = rootView.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("消息");
        //viewpager
        viewpager = rootView.findViewById(R.id.viewpager);
        //缓存两个界面
        viewpager.setOffscreenPageLimit(2);
        //代办父布局
        fragmentMessagLin = rootView.findViewById(R.id.fragment_messag_lin);
        fragmentMessagLin.setOnClickListener(this);
        //已办
        fragmentmessagehavedone = rootView.findViewById(R.id.fragment_message_havedone);
        fragmentmessagehavedone.setOnClickListener(this);
        //代办
        fragmentessageagency = rootView.findViewById(R.id.fragment_message_agency);
        fragments.add(new AgencyPageFragmentZc());
        fragments.add(new HavedonePageFragmentZc());
        viewpager.setAdapter(new FmPagerAdapter(fragments, getFragmentManager()));
    }

    /**
     * viewpagerh滑动处理
     */
    private void initpage() {
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        setTextcolor("#5095F9", "#030303");
                        break;
                    case 1:
                        setTextcolor("#030303", "#5095F9");
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

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_messag_lin:
                setTextcolor("#5095F9", "#030303");
                viewpager.setCurrentItem(0);
                break;
            case R.id.fragment_message_havedone:
                setTextcolor("#030303", "#5095F9");
                viewpager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    /**
     * 设置点击布局颜色
     *
     * @param str
     * @param str1
     */
    public void setTextcolor(String str, String str1) {
        fragmentessageagency.setTextColor(Color.parseColor(str));
        fragmentmessagehavedone.setTextColor(Color.parseColor(str1));
    }
}
