package com.example.administrator.newsdf.GreenDao;

import com.example.administrator.newsdf.App;

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
        App.getDaoInstant().getCollectionDao().insert(shop);
    }
}
