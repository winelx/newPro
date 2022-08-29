package com.example.administrator.yanghu.pzgc.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yanghu.App;
import com.example.administrator.yanghu.GreenDao.LoveDao;
import com.example.administrator.yanghu.GreenDao.Shop;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.check.webview.CheckabfillWebActivity;
import com.example.administrator.yanghu.pzgc.bean.Tab;
import com.example.administrator.yanghu.pzgc.fragment.HomeFragment;
import com.example.administrator.yanghu.pzgc.fragment.HomesFragment;
import com.example.administrator.yanghu.pzgc.utils.SPUtils;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.QrConfig;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.view.ClearEditText;
import com.example.baselibrary.view.PermissionListener;
import com.example.baselibrary.zxing.android.CaptureActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.CookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;


/**
 * @author lx
 * <p>
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 状态图片
     */
    private CheckBox checkBox;

    //用户名密码1
    private ClearEditText username, password;
    private Context mContext;
    private Button login;
    public static Dialog progressDialog = null;
    private static final int REQUEST_CODE = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        //点击背景关闭软键盘
        findViewById(R.id.backgroud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKeyBoard();
            }
        });
        findViewById(R.id.forget_password).setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        password = (ClearEditText) findViewById(R.id.login_password);
        username = (ClearEditText) findViewById(R.id.login_username);
        checkBox = findViewById(R.id.login_pass_img);
        username.setText(SPUtils.getString(mContext, "user", ""));
        password.setText(SPUtils.getString(mContext, "password", ""));
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(mContext, CaptureActivity.class), REQUEST_CODE);

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String permission : deniedPermission) {
                            Toast.makeText(mContext, "被拒绝的权限：" +
                                    permission, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_password:
                ToastUtils.showLongToast("请联系管理员");
                break;
            case R.id.login:
                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setShakeAnimation();
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setShakeAnimation();
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
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
        getDialogs((Activity) mContext, "登陆中...");
        login.setVisibility(View.INVISIBLE);
        HttpUrl httpUrl = HttpUrl.parse(Requests.networks);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeCookie(httpUrl);
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
                        progressDialog.dismiss();
                        progressDialog = null;
                        ToastUtils.showLongToast("网络无法连接到internet");
                        login.setVisibility(View.VISIBLE);
                    }
                });
    }

    void login(final String user, final String password) {
        OkGo.post(Requests.Login)
                .tag("login")
                .params("username", user)
                .params("password", password)
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response respons) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                //删除数据库红点
                                greedao();
                                SPUtils.deleAll(mContext);
                                JSONObject jsom = jsonObject.getJSONObject("data");
                                JSONObject extend = jsonObject.getJSONObject("extend");
                                App.getInstance().jsonId = extend.getString("JSESSIONID");
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
                                try {
                                    SPUtils.putString(mContext, "usertype", jsom.getString("type"));
                                } catch (Exception e) {
                                    SPUtils.putString(mContext, "usertype", "1");
                                }
                                //id
                                SPUtils.putString(mContext, "id", id);
                                //头像
                                SPUtils.putString(mContext, "portrait", portrait);
                                //所在组织ID
                                SPUtils.putString(mContext, "orgId", orgId);
                                //手机号
                                SPUtils.putString(mContext, "phone", moblie);
                                if (extend.getString("qrCodeList") != null) {
                                    JSONArray qclist = extend.getJSONArray("qrCodeList");
                                    for (int i = 0; i < qclist.length(); i++) {
                                        JSONObject jsonOb = qclist.getJSONObject(i);
                                        if ("1".equals(jsonOb.getString("type"))) {
                                            SPUtils.putString(mContext, "androidimg", Requests.networks + Utils.isNull(jsonOb.getString("qrcodeUrl")));
                                        } else if ("2".equals(jsonOb.getString("type"))) {
                                            SPUtils.putString(mContext, "iosimg", Requests.networks + Utils.isNull(jsonOb.getString("qrcodeUrl")));
                                        }
                                    }
                                }
                                //是否保存数据
                                if (checkBox.isChecked()) {
                                    SPUtils.putString(mContext, "user", user);
                                    SPUtils.putString(mContext, "password", password);
                                }
                                toActivity();
                                progressDialog.dismiss();
                                progressDialog = null;
                            } else {
                                progressDialog.dismiss();
                                progressDialog = null;
                                login.setVisibility(View.VISIBLE);
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressDialog.dismiss();
                        progressDialog = null;
                        login.setVisibility(View.VISIBLE);
                        ToastUtils.showLongToast("请确认网络是否通畅");
                    }
                });
    }

    /**
     * 重写返回键
     */
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

    /**
     * @内容: 获取数据举数据
     * @author lx
     * @date: 2019/1/2 0002 下午 5:06
     */
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

    /**
     * 展示dailog
     */
    public static void getDialogs(Activity activity, String str) {
        if (progressDialog == null) {
            progressDialog = new Dialog(activity, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.waiting_dialog);
            //点击外部取消
            progressDialog.setCanceledOnTouchOutside(false);
//            //物理返回键
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            text.setText(str);
            progressDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == QrConfig.REQUEST_UPLOAD) {
                getDialogs(this, "正在处理数据...");
                getBaseUrl(data.getStringExtra("appid"), new NetworkAdapter() {
                    @Override
                    public void onsuccess(String base) {
                        super.onsuccess(base);
                        startActivity(new Intent(App.getInstance(), AddFrileUpdataActivity.class)
                                .putExtra("url", base + data.getStringExtra("url"))
                        );
                    }
                });
            } else if (resultCode == QrConfig.REQUEST_WEB) {
                startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                        .putExtra("url", data.getStringExtra("url")));
            } else if (resultCode == QrConfig.REQUEST_H5) {
                getDialogs(this, "正在处理数据...");
                getBaseUrl(data.getStringExtra("appid"), new NetworkAdapter() {
                    @Override
                    public void onsuccess(String base) {
                        super.onsuccess(base);
                        startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                                .putExtra("url", base + data.getStringExtra("url"))
                                .putExtra("color", "#5096F8"));
                    }
                });
            }
        }

    }

    public void toActivity() {
        if (!TextUtils.isEmpty(SPUtils.getString(mContext, "usertype", ""))) {
            if ("2".equals(SPUtils.getString(mContext, "usertype", ""))) {
                //农民工
                //职员
                startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                        .putExtra("url", Requests.networks + "/h5/study/index.html").putExtra("color", "#E22719"));
                finish();
            } else {
                //职员
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void getBaseUrl(String str, NetworkAdapter adapter) {
        NetWork.getHttp(Api.GetBaseUrl + str, null, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optInt("ret") == 0) {
                        String baseurl = jsonObject.getString("data");
                        adapter.onsuccess(baseurl);
                    } else {
                        ToastUtils.showShortToast("无法解析二维码");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("无法解析二维码");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("无法解析二维码");
            }
        });
    }
}