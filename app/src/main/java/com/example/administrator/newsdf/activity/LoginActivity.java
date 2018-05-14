package com.example.administrator.newsdf.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.baseApplication;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @author lx
 *         <p>
 *         登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 状态图片
     */
    private ImageView img;
    /**
     * 判断用户是否记住密码
     */

    private boolean status = true;
    //用户名密码1
    private EditText username, password;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = baseApplication.getInstance();
        findViewById(R.id.login_pass_lean).setOnClickListener(this);
        findViewById(R.id.forget_password).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        password = (EditText) findViewById(R.id.login_password);
        username = (EditText) findViewById(R.id.login_username);
        img = (ImageView) findViewById(R.id.login_pass_img);
        username.setText(SPUtils.getString(mContext, "user", ""));
        password.setText(SPUtils.getString(mContext, "password", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_pass_lean:
                if (status) {
                    //记住密码
                    img.setBackgroundResource(R.mipmap.login_pass_false);
                    Toast.makeText(this, "不记住", Toast.LENGTH_SHORT).show();
                    status = false;
                } else {
                    //不记住密码
                    img.setBackgroundResource(R.mipmap.login_pass_true);
                    Toast.makeText(this, "记住", Toast.LENGTH_SHORT).show();
                    status = true;
                }
                break;
            case R.id.forget_password:
                ToastUtils.showLongToast("请联系管理员");
                break;
            case R.id.login:
                if (TextUtils.isEmpty(password.getText().toString()) &&
                        TextUtils.isEmpty(username.getText().toString())) {
                    Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                } else {
                    String user = username.getText().toString();
                    String passowd = password.getText().toString();
                    // 网络请求
                    okgo(user, passowd);
                }
                break;
            default:
                break;
        }
    }

    private void okgo(final String user, final String passowd) {
        OkGo.post(Requests.networks)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        login(user, passowd);
                    }

                    //这个错误是网络级错误，不是请求失败的错误
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showLongToast("网络无法连接到internet");
                    }
                });
    }

    void login(final String user, final String password) {
        Dates.getDialog(LoginActivity.this, "登录中...");
        OkGo.post(Requests.Login)
                .params("username", user)
                .params("password", password)
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response respons) {

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int ret = jsonObject.getInt("ret");
                            String msg = jsonObject.getString("msg");
                            ToastUtils.showLongToast(msg);
                            if (ret != 0) {
                                Dates.disDialog();
                            }
                            JSONObject jsom = jsonObject.getJSONObject("data");
                            String id;
                            try {
                                id = jsom.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                id = "";
                            }
                            String portrait;
                            try {
                                //头像 需要拼接公共头
                                portrait = jsom.getString("portrait");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                portrait = "";
                            }
                            String staffId;
                            try {
                                //职员ID
                                staffId = jsom.getString("staffId");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                staffId = "";
                            }
                            String orgName;
                            try {
                                //所在组织名称
                                orgName = jsom.getString("orgName");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                orgName = "";
                            }
                            String staffName;
                            try {
                                //真实姓名
                                staffName = jsom.getString("staffName");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                staffName = "";
                            }
                            String orgId;
                            try {
                                //所在组织id
                                orgId = jsom.getString("orgId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                orgId = "";
                            }
                            String moblie;
                            try {
                                //手机号
                                moblie = jsom.getString("phone");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                moblie = "";
                            }
                            SPUtils.deleAll(mContext);
                            //职员ID
                            SPUtils.putString(mContext, "staffId", staffId);
                            //所在组织名称
                            SPUtils.putString(mContext, "username", orgName);
                            //真实姓名
                            SPUtils.putString(mContext, "staffName", staffName);
                            //id
                            SPUtils.putString(mContext, "id", id);
                            //头像
                            SPUtils.putString(mContext, "portrait", portrait);
                            //所在组织ID
                            SPUtils.putString(mContext, "orgId", orgId);
                            //手机号
                            SPUtils.putString(mContext, "phone", moblie);
                            //是否保存数据
                            if (status) {
                                SPUtils.putString(mContext, "user", user);
                                SPUtils.putString(mContext, "password", password);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showLongToast("请确认网络是否通畅");
                    }
                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}