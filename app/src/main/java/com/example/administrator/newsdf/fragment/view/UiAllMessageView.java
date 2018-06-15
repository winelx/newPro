package com.example.administrator.newsdf.fragment.view;

import com.example.administrator.newsdf.bean.Home_item;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public interface UiAllMessageView {
    void setAdapter(List<String> list, Map<String, List<Home_item>> map);
    void showLoding();
}
