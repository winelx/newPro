package com.example.administrator.newsdf.pzgc.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/8/22 0022.
 */

public class CheckDetails {
    JSONObject json;
    JSONObject json1;

    public CheckDetails(JSONObject json, JSONObject json1) {
        this.json = json;
        this.json1 = json1;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public JSONObject getJson1() {
        return json1;
    }

    public void setJson1(JSONObject json1) {
        this.json1 = json1;
    }
}
