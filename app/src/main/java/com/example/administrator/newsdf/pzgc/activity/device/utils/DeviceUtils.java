package com.example.administrator.newsdf.pzgc.activity.device.utils;

import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/11/27 0027.
 * @description:
 */

public class DeviceUtils {
    public interface AllOnclickLitener {
        void onsuccess(ArrayList<String> list, Map<String, List<Home_item>> getListMap);
    }

    public interface MeOnclickLitener {
        void onsuccess(ArrayList<String> list);
    }

    public void deviceall(final AllOnclickLitener onclickLitener) {
        final ArrayList<String> list = new ArrayList<>();
        final Map<String, List<Home_item>> ListMap = new HashMap<>();
        list.add("测试数据1");
        list.add("测试数据2");
        list.add("测试数据3");
        list.add("测试数据4");
        ArrayList<Home_item> demo1 = new ArrayList<>();
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        demo1.add(new Home_item("册数", "2010-12-12 12:12:00", "", "",
                "组织", "1", "false", "1", "1212", false));
        ListMap.put("测试数据1", demo1);
        ListMap.put("测试数据2", demo1);
        ListMap.put("测试数据3", demo1);
        ListMap.put("测试数据4", demo1);
        onclickLitener.onsuccess(list, ListMap);
//        OkGo.post("")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        onclickLitener.onsuccess(list, ListMap);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });
    }


    public void deviceme(final MeOnclickLitener onclickLitener) {
        final ArrayList<String> list = new ArrayList<>();
        onclickLitener.onsuccess(list);
//        OkGo.post("")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        onclickLitener.onsuccess(list);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//
//                    }
//                });
    }
}
