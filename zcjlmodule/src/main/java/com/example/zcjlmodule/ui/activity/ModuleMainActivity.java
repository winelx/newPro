package com.example.zcjlmodule.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zcjlmodule.presenter.ModuleMainPresenter;
import com.example.zcjlmodule.view.ModuleMainView;
import com.example.zcmodule.R;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;

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
//        mPresenter.register("admin", "123456");
        startActivity(new Intent(this,HomeZcActivity.class));
    }

    //拿到数据
    @Override
    public void getdata(int anem) {
        if (anem==0) {
            startActivity(new Intent(this, HomeZcActivity.class));
        }
    }
}
