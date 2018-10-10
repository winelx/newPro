package com.example.zcjlmodule.presenter;

import android.text.TextUtils;

import com.example.zcjlmodule.contract.ModuleMainContract;
import com.example.zcjlmodule.model.ModuleMainModel;
import com.example.zcmodule.R;

import measure.jjxx.com.baselibrary.frame.MvpPresenter;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ModuleMainPresenter extends MvpPresenter<ModuleMainModel,ModuleMainContract.View> implements ModuleMainContract.Present {

    private static final String TAG = "LoginPresenter";
    @Override
    public void login() {
        if (!checkParameter()) {
            return;
        }
        String account = getIView().getAccount();
        String password = getIView().getPassword();
        String str = mModel.login(account, password);
        if (str.equals("成功")) {
            getIView().loginSuccess(str);
        } else {
            getIView().loginError(str+"1212");
        }

    }
    /**
     * 登录参数校验
     *
     * @return
     */
    private boolean checkParameter() {
        try {
            if (TextUtils.isEmpty(getIView().getAccount())) {
                getIView().loginError(mContext.getString(R.string.toast_account_empty));
                return false;
            }

            if (TextUtils.isEmpty(getIView().getPassword())) {
                getIView().loginError(mContext.getString(R.string.toast_password_empty));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
