package com.example.zcjlmodule.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zcmodule.R;

import measure.jjxx.com.baselibrary.frame.BaseFragment;

/** 
 * description: 征拆首页 我的 界面
 * @author lx
 * date: 2018/10/10 0010 下午 3:01 
 * update: 2018/10/10 0010
 * version:
*/
public class MineFragmentZc extends BaseFragment {
    private View rootView;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_mine_zc, null);
            TextView toolbarTitle =rootView.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("工作");
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }
}
