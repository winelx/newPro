package com.example.baselibrary.base;

import android.arch.lifecycle.ViewModel;


/**
 * @Author lx
 * @创建时间  2019/7/31 0031 14:09
 * @说明 基础的ViewModel 封装错误回调
 **/

public class BaseViewModel extends ViewModel {
    public interface Modelinface {
        void onerror();
    }
    public Modelinface modelinface;
    public void setRequestError(Modelinface modelinfaces) {
        this.modelinface = modelinfaces;
    }


}
