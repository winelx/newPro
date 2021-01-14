package com.example.administrator.fengji.pzgc.fragment.model;

import com.example.administrator.fengji.pzgc.bean.Home_item;

import java.util.List;
import java.util.Map;

/**
 * description: 全部消息界面的mode接口层 (mvp的Model)
 *
 * @author lx
 *         date: 2018/6/14 0014 上午 10:58
 *         update: 2018/6/14 0014
 *         version:
 */
public interface AllMessageModel {
    /**
     * 获取数据的方法
     *
     * @param onClickListener
     */
    void getData(OnClickListener onClickListener);

    /**
     * 接口
     */
    interface OnClickListener {
        void onComple(List<String> list, Map<String, List<Home_item>> map);
        void onError();
    }
}
