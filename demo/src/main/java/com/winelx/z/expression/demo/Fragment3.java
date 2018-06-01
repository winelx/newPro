package com.winelx.z.expression.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;



public class Fragment3 extends Fragment {
    TextView content3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three, null);
        content3 = view.findViewById(R.id.content3);
//        view.findViewById(R.id.okgo1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkGo.post("http://120.79.142.15/pzgc/")
//                                    .params("mobileLogin", true)
//                        .execute(new StringCallback() {
//                                @Override
//                                public void onSuccess(String s, okhttp3.Call call, Response response) {
//                                    two();
//                                }
//
//
//                        });
//            }
//        });
        view.findViewById(R.id.inface3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post("http://120.79.142.15/pzgc/iface/mobile/taskmsg/findWbsTaskMsgByOrg")
                        .params("mobileLogin", true)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, okhttp3.Call call, Response response) {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                content3.setText(s);
                            }
                        });
            }
        });

        return view;

    }

    public void two() {
        OkGo.post("http://120.79.142.15/pzgc/admin/login")
                .params("username", "admin")
                .params("password", "123456")
                .params("mobileLogin", true)
                        .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                Log.i("login", "成功2");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });

    }
}
