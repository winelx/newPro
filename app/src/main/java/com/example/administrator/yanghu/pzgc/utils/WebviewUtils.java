package com.example.administrator.yanghu.pzgc.utils;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.util.Arrays;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class WebviewUtils {
    public void sycCook(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        //移除
        cookieManager.removeSessionCookie();
        //同步cookie
        String cookie = getCookie(url);
        cookieManager.setCookie(url, cookie);
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }

    public void sycCooks(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        //同步cookie
        String cookie = getCookies(cookieManager.getCookie(url));
        cookieManager.setCookie(url, cookie);
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }


    public void sycCook(String url, String weburl) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        //移除
        cookieManager.removeSessionCookie();
        //同步cookie
        cookieManager.setCookie(url, getCookie(weburl));
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }


    public String getCookie(String tr) {
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(tr);
        List<Cookie> cookieList = cookieStore.getCookie(httpUrl);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cookieList.size(); i++) {
            Cookie cookie = cookieList.get(i);
            if ("jjsso".equals(cookie.name()) || "jxhx".equals(cookie.name()) || "gzlq".equals(cookie.name())|| "dtxt".equals(cookie.name())) {
                builder.append(cookie.name()).append("=").append(cookie.value()).append(";");
            }
        }
        return builder.toString();
    }

    public String getCookies(String tr) {
        List<String> data = stringToList(tr);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            String[] str = data.get(i).split("=");
            builder.append(str[0]).append("=").append(str[1]).append(";");
        }
        builder.append(" isMobile").append("=").append("true").append(";");
        return builder.toString();
    }

    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {
        } else {
            String str[] = strs.split(";");
            return Arrays.asList(str);
        }
        return null;
    }
}
