package com.example.administrator.newsdf.model;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public interface Function<Result, Param> {

    Result function(Param... data);
}