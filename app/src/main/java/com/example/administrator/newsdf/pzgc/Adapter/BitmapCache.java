package com.example.administrator.newsdf.pzgc.Adapter;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;

/**
 * 任务列表界面的缓存
 * Created by Administrator on 2018/1/22 0022.
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        int maxSize = 4 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        LogUtil.i("leslie", "get cache " + url);
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        LogUtil.i("leslie", "add cache " + url);
        if (bitmap != null) {
            mCache.put(url, bitmap);
        }
    }
}
