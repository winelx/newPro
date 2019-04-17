package com.example.baselibrary.ui.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.baselibrary.R;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.view.signature.SignatureView;

import java.io.IOException;
import java.util.Calendar;

public class AutographDraw extends Fragment {
    private View view;
    private String paths;
    private Context mContext;
    private SignatureView signatureView;
    private LinearLayout back;
    private SignatureViewActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_autograph_draw, container, false);
        mContext = getActivity();
        activity = (SignatureViewActivity) mContext;
        String str = String.valueOf(Calendar.getInstance().getTimeInMillis());
        paths = mContext.getExternalCacheDir().getPath() + "/signa/image.png";
        signatureView = view.findViewById(R.id.signatureview);
        back = view.findViewById(R.id.draw_back);
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setvertical();
                activity.setItem(0);
            }
        });
        //保存
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //path：保存的地址；clearBlank：是否清除空白区域；blank：空白区域留空距离；
                    boolean lean = signatureView.save(paths, false, 1);
                    //根据返回值判断是否保存成功
                    if (lean) {
                        activity.setvertical();
                        activity.setItem(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //清除
        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clear();
            }
        });
        return view;
    }

}
