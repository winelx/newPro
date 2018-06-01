package com.winelx.z.expression.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    boolean lean1, lean2, lean3;
    public static final String networks1 = "http://192.168.20.80:8090/pzgc/";
    public static final String networks2 = "http://192.168.1.119:8081/pzgc/";
    public static final String networks3 = "http://117.187.27.78:58081/pzgc/";
    public String Login = "admin/login";
    private TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));

            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intone();
            }
        });

    }


    public void intone() {
        OkGo.post("http://192.168.1.119:8081/pzgc/")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        four();
                    }
                });

        OkGo.post("http://117.187.27.78:58081/pzgc/")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        two();
                    }
                });
        OkGo.post("http://120.79.142.15/pzgc/")
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        three();
                    }


                });
    }


    public void four() {
        OkGo.post("http://192.168.1.119:8081/pzgc/admin/login")
                .params("username", "黄启玲")
                .params("password", "1234567")
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                Log.i("login", "成功3");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                                Log.i("login", "成功2");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void three() {
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
