package com.example.administrator.newsdf.GreenDao;

import com.example.administrator.newsdf.BaseApplication;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class HideDao {

    /**
     * 添加数据
     *
     * @param shop
     */
    public static void insertLove(Collection shop) {
        BaseApplication.getDaoInstant().getCollectionDao().insert(shop);
    }
}
