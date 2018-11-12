package com.example.zcjlmodule.presenter;

import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.model.OriginalModel;
import com.example.zcjlmodule.view.OriginalView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BasePresenters;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

/**
 * description: 原始勘丈表的
 *
 * @author lx
 *         date: 2018/10/29 0029 上午 11:15
 *         update: 2018/10/29 0029
 *         version:
 *         activity OriginalZcActivity
 */
public class OriginalPresenter extends BasePresenters<OriginalView> {
    private OriginalModel.Model model;

    public void getdata(String orgId, int page, Map<String,String>map) {
        model = new OriginalModel.OriginalModelPml();
        model.getdata(orgId, page,map, new OriginalModel.OnClicklister() {
            @Override
            public void onSuccess(ArrayList<OriginalZcBean> list) {
                BigDecimal bigDecimal = new BigDecimal("0");
                for (int i = 0; i < list.size(); i++) {
                    BigDecimal price = new BigDecimal(list.get(i).getTotalPrice());
                    bigDecimal = bigDecimal.add(price);
                }
                mView.onSuccess(list,bigDecimal);
            }

            @Override
            public void onError() {
                mView.onError();
            }
        });
    }
}
