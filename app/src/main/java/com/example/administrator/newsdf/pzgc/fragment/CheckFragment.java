package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;

/**
 * description: 首页的检查模块
 *
 * @author lx
 *         date: 2018/8/2 0002 上午 11:14
 *         update: 2018/8/2 0002
 *         version:
 */
public class CheckFragment extends Fragment {
    private View rootView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_check, null);
            mContext = MainActivity.getInstance();

        }
        return rootView;
    }
}
