package com.example.administrator.newsdf.pzgc.bean;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeItemDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：整改问题项
 *{@link  ChagedNoticeItemDetailsAdapter}
 *{@link  ChagedNoticeItemDetailsActivity }
 */
public class NoticeItemDetailsProblem {
    String str;

    public NoticeItemDetailsProblem(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
