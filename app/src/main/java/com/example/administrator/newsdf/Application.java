package com.example.administrator.newsdf;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

public class Application extends SophixApplication {

    private static final String TAG = "SophixApplication";

    //此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆
    @Keep
    @SophixEntry(App.class)
    static class RealApplicationStub {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //如果需要使用MultiDex，需要在此处调用
        initSophix();
    }

    private void initSophix() {
        //对应 aliyun-emas-services.json 中的 hotfix.idSecret
        String idSecret = "24944375-1";
        //对应 aliyun-emas-services.json 中的 emas.appSecret
        String appSecret = "c49220bf28b3602bc4c2e8c2f390fd45";
        //对应 aliyun-emas-services.json 中的 hotfix.rsaSecret
        String rsaSecret = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCmQXFq/6kPCH94HWor39IEd05hnvrYeOCLfRf6oqSkkUiuWZ8hpiNuRqEWENeuG4uw6rBiLkdc6duGZ+l9D5Ejq92UKiUzSlEtnPz5cOK4WxDQYxNVBs0HiwklMl2BWsz2/EFzK+LbMpssirBzuHNHTSFVOt3CWEF2Ky9BLTlFIL4zGdtiVMXc5WFQ9n0Lgr5o83ansVtaaHtGLr62cKEIRtEEM8seko00LZpzkYJyWupvMX8ZoLglYRQneTyEDY0kpjC/JvWWaji3AB4gcHQt1TNigACrrHKO96UVo177Ign/R03XTMzwrFPv+x1gi4wJYTJr2Vtkm8xl0qVj7GrbAgMBAAECggEANuguGPROVRStZHHyeoL0zVzJmhvzJELnhanOnYvRbDg/FLWshGRmFylkUfFGCc43GDqp/gLitqDVS1JOHmtht7Iu/5vbpVDJ9pJ+SbMqbVBGAUx3OgGA/RjB8HLtxqbCQl+VDgBUb6gK727uT4l/KPEkLQ2/50tWczxX4uKYo2dfkgwNh0jnl47Je4yI3j2poMqzPiETEFCY4aSdGWSKT2zrFhVBcK1xJ3n6uenA6U8+9q7K8mfvhjeedyc9w5ojLXJwNb5L8zLdGV5+Y5bwUI01DAOX+MDOEWdSecZgYVrEseLV3dfPGM/BgHMBrDbAuCir1/59WIPbSij9A2rnwQKBgQDp3V4NTY+PSLxoj1TLYNhRSrzPqma5ekavM1r6EdnAS1iUfcd10nxDXBN9Et89BwON8byqznxZX54Ge8QaYwxcf62BM1ltfJMj6tRRSaQXFF0Em0g0tP1cfuNHjuezz8H402UleBr6kEZEAy05B8LWGtkQInKmCqSm4Tyva89rKQKBgQC1/eGoqnsjLP5tQX1OkIUljgFtTrXHF8YYH4lEpJHp6rCDXIyCpUgV1qVHG3mDB4NbeUWwLlfGQ3o70EJAU7kmVeVEw1D9LZU4g74i02xrA9SP8Mv5nVn80eV7RZ9+OiV4xlwIqloeonVdFj4izn0k13mP5gU7WRl/JgH7T39qYwKBgFMIsw0cHb2tAKBLS7sOPp/WXmWJRgHS5WftXv7s+Zhp6CCI7BnsSwyLk6vT2IlNhTiPvQIUYFhHN89rgDwyMMBi4NK/zZ+vHHsjNJFMCUPcig5JqAB2xoJh1wagOapBOMjFHeOI2mNeeGRLkHHwTIBCdhFheb10h/Nsg2RxberJAoGAdrMtkK4I/ItnMumJCkoWncT+R0YUJ9Pov2kqRdw002XgVP1zI+8bkirayAMygPTQh0QU4PGJDGaMfiC6ZWx2EXvquqvN0iP99MtwoxRU3YO1C2EaGSe5Pr3EOF/TxCe2swxlkL1TZDG/7MQKKPeXBgWIYwO7G9dkWtr2ZeyXBVMCgYBb86A0JSGYqk8gNh4XbMWC0G0gyw77puoqGwkp6cLZZrpKqQe03KGybR1CWXglOC/oMVh0Oquz5VtI/fjaDKRy1KWX8CSBZG2DjYtQCbCAmZue+oUUsgkfiUqU3fsQxjbml7DibfNrmdL6QIsTg6FoJZfpRYzmBTEX2ZKfX/e3JA==";
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)  //传入入库Application即可
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setSecretMetaData(idSecret, appSecret, rsaSecret) //对应AndroidManifest中的设置的三个值（若不为null会覆盖AndroidManifest中的）
                .setEnableDebug(false)  //是否调试模式，默认false，调试模式下会输出日志以及不进行补丁签名校验，正式发布时必须为false
                .setEnableFullLog()
                //设置patch加载状态监听器
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    /**
                     * 补丁加载状态会回调给该接口
                     * @param mode 无实际意义，为兼容老版本，默认始终为0
                     * @param code 补丁加载状态码，详情可参考：https://help.aliyun.com/document_detail/61082.html?spm=a2c4g.11186623.2.14.5dbeba6drQHvaN#h2-1-2-2
                     * @param info 补丁加载详细说明
                     * @param handlePatchVersion 当前处理的补丁版本号，0:无，-1:本地补丁，其它:后台补丁
                     */
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            //表明补丁加载成功
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            //表明新补丁生效需要重启，开发者可提醒用户或者强制重启；建议：可以监听用户进入后台事件，然后应用自杀
                            //如果需要在后台重启，建议此处用SharePreference保存状态
                            //可以在监听到CODE_LOAD_RELAUNCH后在合适的时机，调用此方法杀死进程（不可直接
                            //调用Process.killProcess(Process.myPid()）来杀死进程）
                            //instance.killProcessSafely();
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        } else {

                        }
                    }
                }).initialize();
    }
}
