package com.example.administrator.newsdf.zlaq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.zlaq.BaseFragment;


/**
 * description: 统一登录平台首页消息界面
 * @author lx
 * date: 2018/7/2 0002 上午 10:00
 * update: 2018/7/2 0002
 * version:
 */
public class UnifiedMessageFragment extends BaseFragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_un_message, null);
        }
        return rootView;
    }
}
