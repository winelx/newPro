package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

public class CheckDetailsContent {
    String username;
    String results;
    String describe;
    String resultData;
    ArrayList<String> imageList;

    public CheckDetailsContent(String username, String results, String describe, String resultData) {
        this.username = username;
        this.results = results;
        this.describe = describe;
        this.resultData = resultData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }
}
