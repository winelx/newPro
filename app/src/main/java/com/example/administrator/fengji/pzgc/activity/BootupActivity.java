package com.example.administrator.fengji.pzgc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.fengji.App;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.fengji.pzgc.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * description: 启动页
 * @author lx
 * date: 2018/3/9 0009 下午 2:15
 * update: 2018/3/9 0009
 * version:
 */
public class BootupActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bootup);
        //第一次安装完成启动，和home键退出点击launcher icon启动会重复
        // 此代码防止重复创建的问题，
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }
        mContext = BootupActivity.this;
        //清除cooking
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeCookie(httpUrl);
        //判断百度地图的key是否正确
        Dates.getSHA1(getApplicationContext());
        //获取保存的用户名和密码
        final String user = SPUtils.getString(BootupActivity.this, "user", "");
        final String password = SPUtils.getString(BootupActivity.this, "password", "");
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //进行是否登录判断
                if (TextUtils.isEmpty(user)) {
                    //实现页面跳转
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                } else {
                    //如果已经存在，
                    // 先调用退出，然后又再进行登录，不然在cooking失效后。将无法进行数据请求
                    BackTo(user, password);
                }
                return false;
            }
            //表示发送任务
        }).sendEmptyMessageDelayed(0, 1000);
    }

    //假登录
    private void okgo(final String user, final String passowd) {
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeCookie(httpUrl);
        OkGo.post(Requests.networks)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //进行真正的登录
                        login(user, passowd);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //如果走这里，可能是当前网络无法连接该地址
                        startActivity(new Intent(BootupActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    /**
     * 登录
     *
     * @param user
     * @param password
     */
    private void login(final String user, final String password) {
        OkGo.post(Requests.Login)
                .params("username", user)
                .params("password", password)
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String msg, Call call, Response response) {
                        String message;
                        try {
                            JSONObject jsonObject = new JSONObject(msg);
                            int str = jsonObject.getInt("ret");
                            message = jsonObject.getString("msg");
                            JSONObject extend = jsonObject.getJSONObject("extend");
                            App.getInstance().jsonId = extend.getString("JSESSIONID");
                            if (str == 0) {
                                JSONObject jsom = jsonObject.getJSONObject("data");
                                String id = jsom.getString("id");
                                //头像 需要拼接公共头
                                String portrait;
                                try {
                                    portrait = jsom.getString("portrait");
                                } catch (JSONException e) {
                                    portrait = "";
                                }
                                //职员ID
                                String staffId = jsom.getString("staffId");
                                ;//所在组织名称
                                String orgName = jsom.getString("orgName");
                                //真实姓名
                                String staffName;
                                try {
                                    staffName = jsom.getString("staffName");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    staffName = "";
                                }
                                //所在组织id
                                String orgId = jsom.getString("orgId");
                                //手机号
                                String phone = jsom.getString("phone");
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
                                //ID
                                SPUtils.putString(mContext, "orgId", orgId);
                                //手机
                                SPUtils.putString(mContext, "phone", phone);
                                //是否保存数据
                                SPUtils.putString(mContext, "user", user);
                                SPUtils.putString(mContext, "password", password);
                                if (extend.getString("qrCodeList") != null) {
                                    JSONArray qclist = extend.getJSONArray("qrCodeList");
                                    for (int i = 0; i < qclist.length(); i++) {
                                        JSONObject jsonOb = qclist.getJSONObject(i);
                                        if ("1".equals(jsonOb.getString("type"))) {
                                            SPUtils.putString(mContext, "androidimg", Requests.networks+Utils.isNull(jsonOb.getString("qrcodeUrl")));
                                        } else if ("2".equals(jsonOb.getString("type"))) {
                                            SPUtils.putString(mContext, "iosimg", Requests.networks+Utils.isNull(jsonOb.getString("qrcodeUrl")));
                                        }
                                    }
                                }
                                startActivity(new Intent(BootupActivity.this, MainActivity.class));
                                finish();
                            } else {
                                ToastUtils.showLongToast(message);
                                startActivity(new Intent(BootupActivity.this, LoginActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 退出登录
     */
    private void BackTo(final String user, final String password) {
        OkGo.post(Requests.BackTo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //进行登录，登录后重定向
                        okgo(user, password);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        startActivity(new Intent(BootupActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }


}