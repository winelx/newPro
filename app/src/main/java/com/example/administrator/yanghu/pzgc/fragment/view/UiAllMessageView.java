package com.example.administrator.yanghu.pzgc.fragment.view;

import com.example.administrator.yanghu.pzgc.bean.Home_item;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public interface UiAllMessageView {
    void setAdapter(List<String> list, Map<String, List<Home_item>> map);

    void onerror(String str);
}
