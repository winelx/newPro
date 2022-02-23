package com.example.administrator.newsdf.pzgc.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.utils.network.NetworkAdapter;

@SuppressLint("ValidFragment")
public class PopCameraFragment extends DialogFragment {
    private View view;
    private NetworkAdapter adapter;

    public PopCameraFragment(NetworkAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window dialogWindow = getDialog().getWindow();
        if (dialogWindow != null) {
            dialogWindow.getDecorView().setPadding(dip2px(getContext(), 8), 0, dip2px(getContext(), 8), dip2px(getContext(), 20));
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
            lp.windowAnimations = android.R.style.Animation_InputMethod;
            dialogWindow.setAttributes(lp);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.camera_pop_menu, container, false);
        view.findViewById(R.id.btn_camera_pop_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.onsuccess("相机");
                    getDialog().dismiss();
                }

            }
        });
        view.findViewById(R.id.btn_camera_pop_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.onsuccess("相册");
                    getDialog().dismiss();
                }
            }

        });
        view.findViewById(R.id.btn_camera_pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.onerror();
                    getDialog().dismiss();
                }

            }
        });
        return view;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

//使用
    /*     new PopCameraFragment(new NetworkAdapter() {
        @Override
        public void onsuccess(String string) {
            super.onsuccess(string);
            if ("相机".equals(string)) {
                openCamera();
            } else {
                openPhoto();
            }
        }

        @Override
        public void onerror() {
            super.onerror();
        }
    }).show(getFragmentManager(), "dialog");*/

}
