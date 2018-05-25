package com.example.administrator.newsdf.activity.work.inface;

import com.example.administrator.newsdf.bean.Home_item;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public interface CollectionInterface {
    //任务
    int saveCollection(String taskId);//收藏

    int deleteCollection(String taskId);//取消收藏

    //标段
    int saveList(String taskId);//收藏

    int deleteList(String taskId);//取消收藏

    //收藏列表
   List<Home_item> getList();


}
