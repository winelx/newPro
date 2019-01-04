package release;

import android.util.Log;

import com.example.zcjlmodule.BuildConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.zxy.tiny.Tiny;

import measure.jjxx.com.baselibrary.base.BaseApplication;
import measure.jjxx.com.baselibrary.utils.LogUtil;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * @author lx
 * @Created by: 2018/10/9 0009.
 * @description:
 */

public class App extends BaseApplication {
    private static App instance;

        public static App getInstance() {
            return instance;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            instance = this;
            //图片压缩
            Tiny.getInstance().init(this);
            //提示框
            ToastUtlis.getInstance().init(this);
            //网络加载库
            OkGo.init(this);
        //Log配置 根据运行的环境是debug还是打包，控制是否显示日志
        if (BuildConfig.LOG_DEBUG) {
            LogUtil.init(true, Log.VERBOSE);
        } else {
            LogUtil.init(false);
        }
        //网络加载库配置
        OkGo.getInstance()
                //全局默认加载超时时间
                .setConnectTimeout(2000)
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(0)
                //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore());
    }
}
