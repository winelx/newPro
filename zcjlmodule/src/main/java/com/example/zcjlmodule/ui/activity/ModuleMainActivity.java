package com.example.zcjlmodule.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zcjlmodule.contract.ModuleMainContract;
import com.example.zcjlmodule.presenter.ModuleMainPresenter;
import com.example.zcmodule.R;

import measure.jjxx.com.baselibrary.base.BaseView;
import measure.jjxx.com.baselibrary.frame.MvpActivity;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

public class ModuleMainActivity extends MvpActivity<ModuleMainPresenter> implements ModuleMainContract.View,View.OnClickListener {
    private EditText edtAccount, edtPassword;
    private Button btnLogin;
    private Context  mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zc_activity_module_main);
        mContext=this;
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
    public BaseView getBaseView() {
        return this;
    }
    //获取参数
    @Override
    public String getAccount() {
        return edtAccount.getText().toString();
    }
    //获取参数
    @Override
    public String getPassword() {
        return edtPassword.getText().toString();
    }
    //成功
    @Override
    public void loginSuccess(String msg) {
        ToastUtlis.getInstance().showShortToast(msg);
        startActivity(new Intent(mContext,HomeActivityZc.class));
    }
    //失败
    @Override
    public void loginError(String errorMsg) {
        ToastUtlis.getInstance().showShortToast(errorMsg);
    }

    @Override
    public void onClick(View view) {
        mPresenter.login();
    }
}
