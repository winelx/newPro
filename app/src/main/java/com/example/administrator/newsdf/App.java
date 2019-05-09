package com.example.administrator.newsdf;


import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.util.Log;

import com.example.administrator.newsdf.GreenDao.DaoMaster;
import com.example.administrator.newsdf.GreenDao.DaoSession;
import com.example.administrator.newsdf.pzgc.service.LocationService;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.PicassoImageLoader;
import com.example.administrator.newsdf.pzgc.view.PieChartBeans;
import com.example.baselibrary.glide.ProgressInterceptor;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.zxy.tiny.Tiny;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * @author lx
 * Created by Administrator on 2017/11/21 0021.
 */

public class App extends Application {
    public String jsonId, imagepath;
    /**
     * 用于存放所有启动的Activity的集合
     */
    private List<Activity> oList;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private static DaoSession daoSession;
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        oList = new ArrayList<>();
        setupDatabase();
        instance = this;
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载更多数据";
        //网络加载库
        OkGo.init(this);
        //图片压缩
        Tiny.getInstance().init(this);
        //字体图标
        Iconify.with(new FontAwesomeModule());
        //网络加载库配置
        OkGo.getInstance()
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(0)
                .addInterceptor(new ProgressInterceptor())
                //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore());
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        /**
         * 图片预览库
         */
        //一般在Application初始化配置一次就可以
        ImagePicker imagePicker = ImagePicker.getInstance();
        //设置图片加载器
        imagePicker.setImageLoader(new PicassoImageLoader());
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        //允许裁剪（单选才有效）
        imagePicker.setCrop(false);
        //是否按矩形区域保存
        imagePicker.setSaveRectangle(true);
        //选中数量限制
        imagePicker.setSelectLimit(9);
        //裁剪框的形状
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusWidth(800);
        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);
        //保存文件的宽度。单位像素
        imagePicker.setOutPutX(1000);
        //保存文件的高度。单位像素
        imagePicker.setOutPutY(1000);
        //开启极光推送
        JPushInterface.init(this);
//        JPushInterface.setDebugMode(true);
        //Log配置 根据运行的环境是debug还是打包，控制是否显示日志
        if (BuildConfig.LOG_DEBUG) {
            LogUtil.init(true, Log.VERBOSE);
        } else {
            LogUtil.init(false);
        }
        imagepath = getExternalCacheDir().getPath().replace("cache", "jpg/");
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static ArrayList<PieChartBeans> getlist() {
        ArrayList<PieChartBeans> list = new ArrayList<>();
        list.add(new PieChartBeans("已完成", 25.0f, "#5096F8"));
        list.add(new PieChartBeans("未完成", 45.0f, "#f88c37"));
        list.add(new PieChartBeans("已启动", 35.0f, "#e2c1bfc1"));
        return list;
    }
}
