package com.example.zcjlmodule.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zcmodule.R;

import java.io.File;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseFragment;
import measure.jjxx.com.baselibrary.utils.TakePictureManager;


/**
 * description: 征拆首页 我的 界面  （承载界面HomeZcActivity）
 *
 * @author lx
 *         date: 2018/10/10 0010 下午 3:01
 *         update: 2018/10/10 0010
 *         version:
 */
public class MineFragmentZc extends BaseFragment {
    private View rootView;
    private Context mContext;
    private TextView text;
    private TakePictureManager takePictureManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_mine_zc, null);
            TextView toolbarTitle = rootView.findViewById(R.id.toolbar_title);
            text = rootView.findViewById(R.id.camera);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePictureManager = new TakePictureManager(MineFragmentZc.this);

                    takePictureManager.startTakeWayByCarema();
                    takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                        @Override
                        public void successful(boolean isTailor, File outFile, Uri filePath) {

                        }

                        @Override
                        public void failed(int errorCode, List<String> deniedPermissions) {

                        }
                    });
                }
            });
            toolbarTitle.setText("我的");
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }


        return rootView;
    }

    //相机的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);

    }


}
