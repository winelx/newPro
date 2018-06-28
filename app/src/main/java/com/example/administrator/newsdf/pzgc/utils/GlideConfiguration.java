package com.example.administrator.newsdf.pzgc.utils;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;


/**
 * Created by zhaoyong on 2016/6/16.
 * Glide配置文件
 */
public class GlideConfiguration extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 20mb
        int memoryCacheSizeBytes = 1024 * 1024 * 250;
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
    }


}