package com.example.administrator.newsdf.pzgc.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Response;


/**
 * @author lx
 *         <p>
 *         登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
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
    private RelativeLayout backgroud;
    private Dialog progressDialog;
    private List<Cookie> cookiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = App.getInstance();
        //点击背景关闭软键盘
        findViewById(R.id.backgroud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKeyBoard();
            }
        });
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
                    status = false;
                } else {
                    //不记住密码
                    img.setBackgroundResource(R.mipmap.login_pass_true);
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
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeCookie(httpUrl);
        OkGo.post(Requests.BackTo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int code = jsonObject.getInt("ret");
                            if (code != 1) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
        loading();
        OkGo.post(Requests.Login)
                .params("username", user)
                .params("password", password)
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response respons) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int ret = jsonObject.getInt("ret");
                            ToastUtils.showLongToast(jsonObject.getString("msg"));
                            //删除数据库红点
                            greedao();
                            SPUtils.deleAll(mContext);
                            JSONObject jsom = jsonObject.getJSONObject("data");
                            JSONObject extend = jsonObject.getJSONObject("extend");
                            App.getInstance().jsonId=extend.getString("JSESSIONID");
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
//                            CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
//                            HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
//                            List<Cookie> cookies = cookieStore.getCookie(httpUrl);
//                            synCookies(mContext, cookies.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressDialog.dismiss();
                        ToastUtils.showLongToast("请确认网络是否通畅");
                    }
                });

    }

    //重写返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent MyIntent = new Intent(Intent.ACTION_MAIN);
            MyIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(MyIntent);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    List<Shop> list;

    //获取数据举数据
    private void greedao() {
        list = LoveDao.JPushCart();
        Message mes = new Message();
        handler.sendMessage(mes);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /**
             * 删除数据库数据，改变状态
             */
            for (int i = 0; i < list.size(); i++) {
                LoveDao.deleteLove(list.get(i).getId());
            }
        }
    };

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void loading() {
        progressDialog = new Dialog(LoginActivity.this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.waiting_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        text.setText("登录中...");
        progressDialog.show();
    }



}