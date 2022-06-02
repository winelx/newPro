package com.example.administrator.newsdf.pzgc.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.LoginActivity;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 修改密码
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:43
 * update: 2018/2/6 0006
 * version:
 */
public class PasswordActvity extends BaseActivity implements View.OnClickListener {
    private Button commint;
    private AppCompatEditText pass_old, pass_new, pass_newtoo;
    private ImageView pass_old_icon, pass_new_icon, pass_newtoo_icon;
    private boolean oldstatus = false, newstatus = false, newstatustoo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_actvity);
        pass_old = findViewById(R.id.password_old);
        pass_new = findViewById(R.id.password_new);
        pass_newtoo = findViewById(R.id.password_newtoo);
        pass_old_icon = findViewById(R.id.password_old_icon);
        pass_new_icon = findViewById(R.id.password_new_icon);
        pass_newtoo_icon = findViewById(R.id.password_newtoo_icon);
        pass_old_icon.setBackgroundResource(R.mipmap.pwd_gone);
        pass_new_icon.setBackgroundResource(R.mipmap.pwd_gone);
        pass_newtoo_icon.setBackgroundResource(R.mipmap.pwd_gone);
        pass_old_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_old.requestFocus();
                if (oldstatus) {
                    pass_old_icon.setBackgroundResource(R.mipmap.pwd_gone);
                    pass_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    oldstatus = false;
                } else {
                    pass_old_icon.setBackgroundResource(R.mipmap.pwd_version);
                    pass_old.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    oldstatus = true;
                }
            }
        });
        pass_new_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_new.requestFocus();
                if (oldstatus) {
                    pass_new_icon.setBackgroundResource(R.mipmap.pwd_gone);
                    pass_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newstatus = false;
                } else {
                    pass_new_icon.setBackgroundResource(R.mipmap.pwd_version);
                    pass_new.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newstatus = true;
                }
            }
        });
        pass_newtoo_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_newtoo.requestFocus();
                if (oldstatus) {
                    pass_newtoo_icon.setBackgroundResource(R.mipmap.pwd_gone);
                    pass_newtoo.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newstatustoo = false;
                } else {
                    pass_newtoo_icon.setBackgroundResource(R.mipmap.pwd_version);
                    pass_newtoo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newstatustoo = true;
                }
            }
        });
        findViewById(R.id.password_commint).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String old = pass_old.getText().toString();
        String news = pass_new.getText().toString();
        String newtoo = pass_newtoo.getText().toString();
        switch (v.getId()) {
            //修改密码
            case R.id.password_commint:
                if (TextUtils.isEmpty(news) && TextUtils.isEmpty(old) && TextUtils.isEmpty(newtoo)) {
                    Toast.makeText(this, "还有未填项", Toast.LENGTH_SHORT).show();
                } else {
                    if (news.equals(newtoo)) {
                        okgo(old, newtoo);
                    } else {
                        Toast.makeText(this, "两次输入不相同", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * 网络请求
     */
    @SuppressLint("NewApi")
    void okgo(String old, String news) {
        OkGo.post(Requests.AlterPwd)
                .params("oldPwd", Base64.getEncoder().encodeToString(old.getBytes()))
                .params("newPwd", Base64.getEncoder().encodeToString(news.getBytes()))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                BackTo();
                                Toast.makeText(PasswordActvity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PasswordActvity.this, LoginActivity.class));
                                MainActivity activity = MainActivity.getInstance();
                                activity.finish();
                                finish();
                            } else {
                                Toast.makeText(PasswordActvity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
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
    private void BackTo() {
        OkGo.post(Requests.BackTo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }


}
