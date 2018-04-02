package com.example.administrator.newsdf.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.activity.MainActivity;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


/**
 * description: 极光推送数据接收
 *
 * @author lx
 *         date: 2018/3/26 0026 下午 1:30
 *         update: 2018/3/26 0026
 *         version:
 */
/**
 * description: 极光推送数据接收
 * @author lx
 * date: 2018/3/26 0026 下午 1:30
 * update: 2018/3/26 0026
 * version:
 */
public class PushReceiver extends BroadcastReceiver {
    private Context context;
    Dates dates =new Dates();
    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        final Set<String> keys = bundle.keySet();
        final JSONObject json = new JSONObject();
        for (String key : keys) {
            final Object val = bundle.get(key);
            try {
                json.put(key, val);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final String pushAction = intent.getAction();
        if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            //处理接收到的信息
            dates.addPut();
            context=MainActivity.getInstance();
            try {
                List<Shop> list = LoveDao.JPushCart();
                if (list.size() > 0) {
                    LogUtil.i("ss", "有数据");
                } else {
                    LogUtil.i("ss", "保存数据");
                    Shop shop = new Shop();
                    shop.setType(Shop.TYPE_JPUSH);
                    shop.setName("消息");
                    LoveDao.insertLove(shop);
                }
                MainActivity activity = (MainActivity) context;
                activity.getRedPoint();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            onReceivedMessage(bundle);
        } else if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            //打开相应的Notification
            onOpenNotification(context, bundle);
        }
    }


    private void onReceivedMessage(Bundle bundle) {
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    private void onOpenNotification(Context context, Bundle bundle) {
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final Bundle openActivityBundle = new Bundle();
        final Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(openActivityBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);
    }
}