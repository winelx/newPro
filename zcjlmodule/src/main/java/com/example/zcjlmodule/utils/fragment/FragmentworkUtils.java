package com.example.zcjlmodule.utils.fragment;

import com.example.zcjlmodule.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 工作界面帮助类
 *
 * @author lx
 *         date: 2018/11/16 0016 下午 3:09
 *         update: 2018/11/16 0016
 *         version:
 */
public class FragmentworkUtils {
    public interface Callback {
        void onsuccess();

        void onerror();
    }


    public void getmenu(final Callback callback) {
        OkGo.get(Api.GETMENU)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        callback.onsuccess();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onerror();
                    }
                });
    }
}
