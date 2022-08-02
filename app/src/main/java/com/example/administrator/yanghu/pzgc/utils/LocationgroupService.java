package com.example.administrator.yanghu.pzgc.utils;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.yanghu.pzgc.bean.LodingBean;
import com.example.baselibrary.utils.rx.LiveDataBus;

import java.math.BigDecimal;


/**
 * @author Administrator
 */
public class LocationgroupService {
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = null;
    private Context mContext;

    public LocationgroupService(Context mContext) {
        this.mContext = mContext;

    }

    public void startLocation() {
        try {
            if (mLocationClient == null) {
                //声明LocationClient类
                mLocationClient = new LocationClient(mContext);
                myListener = new MyLocationListener();
                //注册监听函数
                mLocationClient.registerLocationListener(myListener);
                //定位初始化
                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true);
                option.setIsNeedAddress(true);
                option.setCoorType("gcj02");
                option.setScanSpan(5000);
                mLocationClient.setLocOption(option);
                mLocationClient.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLocation() {
        myListener = null;
        mLocationClient.stop();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            int errorCode = location.getLocType();
            if (errorCode == 61 || errorCode == 161) {
                if (!TextUtils.isEmpty(BigDecimal.valueOf(location.getLatitude()).toString())) {
                    LiveDataBus.get().with("loding").setValue(new LodingBean(location.getAddrStr(),
                            BigDecimal.valueOf(location.getLatitude()).toString(),
                            BigDecimal.valueOf(location.getLongitude()).toString()));
                }
            }
        }
    }


}
