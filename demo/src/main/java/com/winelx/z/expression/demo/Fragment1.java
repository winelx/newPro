package com.winelx.z.expression.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


public class Fragment1 extends Fragment {
    TextView content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one, null);
        content = view.findViewById(R.id.content);
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.get("http://192.168.1.119:8081/pzgc/iface/mobile/taskmsg/findWbsTaskMsgByOrg")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, okhttp3.Call call, Response response) {
                                 Toast.makeText(getActivity(), s+"", Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int ret = jsonObject.getInt("ret");
                                    if (ret == 0) {
                                        content.setText(s);
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
