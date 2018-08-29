package com.example.administrator.newsdf.zlaq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.zlaq.presenter.UnifiedPresenter;
import com.example.administrator.newsdf.zlaq.utils.StatusBarUtil;
import com.example.administrator.newsdf.zlaq.view.UnifiedView;


/**
 * description: 统一登录界面
 *
 * @author lx
 *         date: 2018/6/28 0028 下午 4:22
 *         update: 2018/6/28 0028
 *         version:
 */

public class UnifiedLoginActivity extends BaseActivity implements UnifiedView {
    private EditText unfiedBlock, unfiedPassword, unfiedUsername;
    private Button login;
    private UnifiedPresenter mUnifiedPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified);
        StatusBarUtil.setTranslucent(UnifiedLoginActivity.this, 0);
        //presenter对象
        mUnifiedPresenter = new UnifiedPresenter(this);
        unfiedBlock = (EditText) findViewById(R.id.unfied_block);
        unfiedPassword = (EditText) findViewById(R.id.unfied_password);
        unfiedUsername = (EditText) findViewById(R.id.unfied_username);
        login = (Button) findViewById(R.id.unfied_login);
        //new UnifiedPresenter(this).getMode();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = getpass();
                String username = getuser();
                if (username != null && password != null) {
                    ToastUtils.showShortToast("获取信息成功");
                    mUnifiedPresenter.getMode(username, password);
                }
            }
        });
    }

    //登录
    @Override
    public void successful() {
        ToastUtils.showLongToast("登录成功");
        startActivity(new Intent(UnifiedLoginActivity.this, UnifiedHomeActivity.class));
    }

    private String getuser() {
        String username = unfiedUsername.getText().toString();
        if (!username.isEmpty()) {
            return username;
        } else {
            ToastUtils.showShortToast("用户名不能为空");
            return null;
        }
    }

    private String getpass() {
        String password = unfiedPassword.getText().toString();
        if (!password.isEmpty()) {
            return password;
        } else {
            ToastUtils.showShortToast("密码不能为空");
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消绑定persenter
        if (mUnifiedPresenter != null) {
            mUnifiedPresenter.destory();
        }
    }

}
