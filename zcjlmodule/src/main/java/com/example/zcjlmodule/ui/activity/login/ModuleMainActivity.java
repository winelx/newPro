package com.example.zcjlmodule.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.example.zcjlmodule.R;
import com.example.zcjlmodule.presenter.ModuleMainPresenter;
import com.example.zcjlmodule.ui.activity.HomeZcActivity;

import com.example.zcjlmodule.view.ModuleMainView;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.BaseDialogUtils;
import measure.jjxx.com.baselibrary.utils.BaseUtils;
import measure.jjxx.com.baselibrary.view.top_snackbar.BaseTransientBottomBar;
import measure.jjxx.com.baselibrary.view.top_snackbar.TopSnackBar;


/**
 * description: //登录页
 *
 * @author lx
 *         date: 2018/10/26 0026 上午 10:09
 *         update: 2018/10/26 0026
 *         version:
 */
public class ModuleMainActivity extends BaseMvpActivity<ModuleMainPresenter> implements ModuleMainView, View.OnClickListener {
    private EditText edtAccount, edtPassword;
    private LinearLayout mainbackground;
    private Button btnLogin;
    private Context mContext;
    private BaseUtils baseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zc_activity_module_main);
        mContext = this;
        baseUtils = new BaseUtils();
        mPresenter = new ModuleMainPresenter();
        mPresenter.mView = this;
        initViews();
    }

    //初始化参数
    private void initViews() {
        mainbackground = (LinearLayout) findViewById(R.id.main_background);
        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        mainbackground.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                baseUtils.hidekeyboard(this,edtAccount);
                BaseDialogUtils.getDialog(this, "登录中..", false);
                String name = edtAccount.getText().toString();
                String password = edtPassword.getText().toString();
                if (!name.isEmpty() && !password.isEmpty()) {
                    //登录
                    mPresenter.register(name, password);
                } else {
                    BaseDialogUtils.dialog.dismiss();
                    TopSnackBar.make(btnLogin, "用户名或密码为空", BaseTransientBottomBar.LENGTH_SHORT).show();

                }
//             startActivity(new Intent(mContext,OriginalZcActivity.class));
                break;
            case R.id.main_background:
                baseUtils.hidekeyboard(this, edtAccount);
                break;
            default:
                break;
        }

    }

    //拿到数据
    @Override
    public void getdata(int ret) {
        BaseDialogUtils.dialog.dismiss();
        if (ret == 0) {
            startActivity(new Intent(this, HomeZcActivity.class));
            finish();
        }
    }


    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getSupportFragmentManager().popBackStack();
            Intent MyIntent = new Intent(Intent.ACTION_MAIN);
            MyIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(MyIntent);
            android.os.Process.killProcess(android.os.Process.myPid());
            finish();
            return true;
        }
        return true;
    }
}
