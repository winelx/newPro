package com.example.administrator.newsdf.pzgc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class ZLAQActivity extends AppCompatActivity {
    private TextView url, content;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        url= (TextView) findViewById(R.id.url);
        content= (TextView) findViewById(R.id.content);
        editText= (EditText) findViewById(R.id.editext);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post("http://192.168.20.79:1010/admin/")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                OkGo.post("http://192.168.20.79:1010/admin/login")
                                        .params("username", "admin")
                                        .params("password", "123456")
                                        .params("mobileLogin", true)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(s);
                                                    int ret = jsonObject.getInt("ret");
                                                    if (ret == 0) {
                                                        ToastUtils.showLongToast("登录成功");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                            }
                        });
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post("http://192.168.20.79:1010/admin/logout")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int ret = jsonObject.getInt("ret");
                                    if (ret == 0) {
                                        ToastUtils.showLongToast("登录成功");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String str = editText.getText().toString();
//                url.setText(str);
//                OkGo.get(str).execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        content.setText(s);
//                    }
//                });
            }
        });

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                url.setText(str);
//                OkGo.get(str).execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        content.setText(s);
//                    }
//                });

                OkGo.get("http://192.168.20.79:9090/admin/sso/toSSO")
                        .params("key", "IixrHmRlOk3YvMdu+VKBG8wSFRAub/BlojYj0u4PdMZ7I7DL0mgww9d/HpNJokg4")
                        .params("mobile", true)
                        .params("jsonpCallback", "callback")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                               content.setText(s);
                            }
                        });
            }
        });

    }
}
