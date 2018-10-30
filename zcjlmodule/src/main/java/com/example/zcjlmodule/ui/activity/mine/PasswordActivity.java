package com.example.zcjlmodule.ui.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import okhttp3.Call;
import okhttp3.Response;
/** 
 * description: 修改密码
 * @author lx
 * date: 2018/10/29 0029 上午 11:16 
 * update: 2018/10/29 0029
 * version: 
*/
public class PasswordActivity extends BaseActivity {
    private EditText passwordOld, passwordNew, passwordNewtoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_zc);
        passwordOld = (EditText) findViewById(R.id.password_old);
        passwordNew = (EditText) findViewById(R.id.password_new);
        passwordNewtoo = (EditText) findViewById(R.id.password_newtoo);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("修改密码");
        findViewById(R.id.password_commint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(passwordOld.getText().toString()) || TextUtils.isEmpty(passwordNew.getText().toString()) || TextUtils.isEmpty(passwordNewtoo.getText().toString())) {
                    ToastUtlis.getInstance().showShortToast("输入框内容不能为空");
                } else {
                    if (passwordNew.getText().toString().length() > 5) {
                        if (passwordNew.getText().toString().equals(passwordNewtoo.getText().toString())) {
                            //修改密码
                            password(passwordOld.getText().toString(), passwordNew.getText().toString());
                        } else {
                            ToastUtlis.getInstance().showShortToast("新密码两次输入不一致");
                        }
                    } else {
                        ToastUtlis.getInstance().showShortToast("密码长度大于6");
                    }
                }
            }
        });
    }

    public void password(String oldPwd, String newPwd) {
        OkGo.post(Api.PASSWORD)
                .params("oldPwd", oldPwd)
                .params("newPwd", newPwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ToastUtlis.getInstance().showShortToast("修改密码成功");
                                passwordOld.setText("");
                                passwordNewtoo.setText("");
                                passwordNew.setText("");
                            } else {
                                ToastUtlis.getInstance().showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
