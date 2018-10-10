package com.example.zcjlmodule.contract;

import measure.jjxx.com.baselibrary.base.BasePresenter;
import measure.jjxx.com.baselibrary.base.BaseView;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ModuleMainContract {
    public interface View extends BaseView {
        String getAccount();
        String getPassword();
        void loginSuccess(String msg);
        void loginError(String errorMsg);
    }

    public interface Present extends BasePresenter {
        void login();
    }

}
