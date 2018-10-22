package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.model.DetailedlistModel;
import com.example.zcjlmodule.model.ModuleMainBaseViewIpm;
import com.example.zcjlmodule.view.DetailedlistView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:支付清册
 */

public class DetailedlistPresenter extends BasePresenters<DetailedlistView> {
    private DetailedlistModel.DetailedlistModelIpm model;

    public DetailedlistPresenter() {

    }

    public void register(String orgId) {
        //实例model
        model = new DetailedlistModel.DetailedlistModelIpm();
        //请求数据并返回结果
        String str = model.register(orgId);
        //处理结果
        ArrayList<PayDetailedlistBean> list=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            list.add(new PayDetailedlistBean("测试数据" + i, "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据", "测试数据"));

        }
        mView.getdata(list);
    }
}
