package com.winelx.z.expression.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    boolean lean1, lean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lean1 && lean2) {
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }
            }
        });


    }

    public void intone() {
//        OkGo.post(Requests.networks)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        login(user, passowd);
//                    }
//                    //这个错误是网络级错误，不是请求失败的错误
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        ToastUtils.showLongToast("网络无法连接到internet");
//                    }
//                });

        OkGo.post("http://117.187.27.78:58081/pzgc/")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {

                    }
                });
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
                                lean2 = true;
                                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void three() {
        OkGo.post("http://192.168.123.1:8080/baseframe/admin/login")
                .params("", "")
                .params("", "")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                lean1 = true;
                                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
