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

import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class Fragment2 extends Fragment {
    TextView content2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two, null);

        content2 = view.findViewById(R.id.content2);
//        view.findViewById(R.id.okgo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkGo.post("http://117.187.27.78:58081/pzgc/")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, okhttp3.Call call, Response response) {
//                                two();
//                            }
//                        });
//            }
//        });
        view.findViewById(R.id.inface2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post("http://117.187.27.78:58081/pzgc/iface/mobile/taskmsg/findWbsTaskMsgByOrg")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, okhttp3.Call call, Response response) {
                                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                content2.setText(s);
                            }
                        });
            }
        });
        return view;

    }

    public void two() {
        OkGo.post("http://117.187.27.78:58081/pzgc/admin/login")
                .params("username", "黄启玲")
                .params("password", "1234567")
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
                });
    }
}
