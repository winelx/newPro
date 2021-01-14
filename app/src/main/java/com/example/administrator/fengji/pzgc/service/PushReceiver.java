package com.example.administrator.fengji.pzgc.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.administrator.fengji.GreenDao.LoveDao;
import com.example.administrator.fengji.GreenDao.Shop;
import com.example.administrator.fengji.R;

import com.example.administrator.fengji.pzgc.activity.MainActivity;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.rx.RxBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * description: 极光推送数据接收
 *
 * @author lx
 * date: 2018/3/26 0026 下午 1:30
 * update: 2018/3/26 0026
 * version:
 */
public class PushReceiver extends BroadcastReceiver {
    String TAG = "PushReceiver";
    Dates dates = new Dates();
    private static String CHANNEL_ID = "channel_id";   //通道渠道id
    public String CHANEL_NAME = "chanel_name"; //通道渠道名称

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            LogUtil.d(TAG, "[MyReceiver] 接收 Registration Id : ");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            CHANNEL_ID = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            //  show(context, JPushInterface.EXTRA_TITLE, JPushInterface.EXTRA_MESSAGE);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // 在这里可以做些统计，或者做些其他工作
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String noticeType = null;
            try {
                JSONObject jsonObject = new JSONObject(extras);
                noticeType = jsonObject.getString("noticeType");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //1刷新消息首页，2刷新我的任务
            if ("2".equals(noticeType)) {
                //刷新消息页面
                RxBus.getInstance().send("home");
            } else {
                dates.addPut();
                context = MainActivity.getInstance();
                //保存推送消息，
                try {
                    List<Shop> list = LoveDao.JPushCart();
                    //如果还有未读消息，就不往数据库加数据
                    if (list.size() == 0) {
                        Shop shop = new Shop();
                        shop.setType(Shop.TYPE_JPUSH);
                        shop.setName("消息");
                        LoveDao.insertLove(shop);
                    }
                    //调用方法，让Mainactivityu 显示小红点
                    MainActivity activity = (MainActivity) context;
                    activity.getRedPoint();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            onOpenNotification(context, bundle);
        } else {
            //+ intent.getAction()
//            LogUtil.d(TAG, "Unhandled intent - " );
        }
    }

    private void onOpenNotification(Context context, Bundle bundle) {
        Bundle openActivityBundle = new Bundle();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(openActivityBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void show(Context context, String content, String title) {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建 通知通道  channelid和channelname是必须的（自己命名就好）
            channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN);//小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
        }
        Notification notification;
        //获取Notification实例   获取Notification实例有很多方法处理
        // 在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，毕竟16以下的Android机很少了，如果非要全面兼容可以用）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //向上兼容 用Notification.Builder构造notification对象
            notification = new Notification.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.launcher)
                    .build();
        } else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            notification = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.launcher)
                    .build();
        }
        //发送通知
        int notifiId = 1;
        //创建一个通知管理器
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notifiId, notification);
    }
}