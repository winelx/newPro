package com.example.zcjlmodule.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.presenter.ModuleMainPresenter;
import com.example.zcjlmodule.view.ModuleMainView;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.BaseDialog;

//登录页
public class ModuleMainActivity extends BaseMvpActivity<ModuleMainPresenter> implements ModuleMainView, View.OnClickListener {
    private EditText edtAccount, edtPassword;
    private Button btnLogin;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zc_activity_module_main);
        mContext = this;
        mPresenter = new ModuleMainPresenter();
        mPresenter.mView = this;
        initViews();
    }

    //初始化参数
    private void initViews() {
        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        BaseDialog.getDialog(this, "登录中..", false);
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                BaseDialog.dialog.dismiss();
                return false;
                //表示延迟3秒发送任务
            }
        }).sendEmptyMessageDelayed(0, 3000);

//        String name = edtAccount.getText().toString();
//        String password = edtPassword.getText().toString();
//        if (!name.isEmpty() && !password.isEmpty()) {
//            mPresenter.register(name, password);
//        } else {
//            ToastUtlis.getInstance().showShortToast("用户名或密码为空");
//        }
    }

    //拿到数据
    @Override
    public void getdata(int ret) {
        if (ret == 0) {
            startActivity(new Intent(this, HomeZcActivity.class));
            finish();
        }
    }
}
