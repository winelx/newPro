package com.winelx.z.expression.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class Fragment3 extends Fragment {
    TextView content3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three, null);
        content3 = view.findViewById(R.id.content3);
        view.findViewById(R.id.okgo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.get("http://192.168.20.33:8080/user/get_information.do")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, okhttp3.Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int ret = jsonObject.getInt("stauts");
                                    if (ret == 0) {
                                        content3.setText(s);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        return view;

    }
}
