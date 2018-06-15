package com.example.administrator.newsdf.GreenDao;

import com.example.administrator.newsdf.BaseApplication;

import java.util.List;

/**
 * Created by handsome on 2016/4/19.
 */
public class LoveDao {

    /**
     * 添加数据
     *
     * @param shop
     */
    public static void insertLove(Shop shop) {
        BaseApplication.getDaoInstant().getShopDao().insert(shop);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLove(long id) {
        BaseApplication.getDaoInstant().getShopDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateLove(Shop shop) {
        BaseApplication.getDaoInstant().getShopDao().update(shop);
    }

    /**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     */
    //未上传
    public static List<Shop> queryLove() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(Shop.TYPE_LOVE)).list();
    }

    // 下载图片
    public static List<Shop> queryCart() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(Shop.TYPE_CART)).list();
    }

    //用户置顶
    public static List<Shop> ALLCart() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(Shop.TYPE_ALL)).list();
    }

    //用户收藏
    public static List<Shop> MineHide() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(Shop.TYPE_HIDE)).list();
    }

    //用户推送
    public static List<Shop> JPushCart() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(Shop.TYPE_JPUSH)).list();
    }

}
