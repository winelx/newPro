package com.example.baselibrary.ui.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.baselibrary.R;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.view.signature.SignatureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

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
                        File file = new File(paths);
                        ArrayList<File> files = new ArrayList<>();
                        files.add(file);
                        //请求地址，请求参数，上传文件，表单提交，回调
                        NetWork.postHttp(Requests.UPLOADPERSONSIGNATURE, new HashMap<String, String>(), files, false, new NetWork.networkCallBack() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if (jsonObject.getInt("ret") == 0) {
                                        Toast.makeText(mContext, "签名上传成功", Toast.LENGTH_SHORT).show();
                                        activity.setvertical();
                                        activity.setItem(0);
                                    } else {
                                        Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        Snackbar.make(back,"签名录入失败",Snackbar.LENGTH_SHORT).show();
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
