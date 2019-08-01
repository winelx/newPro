package com.example.administrator.newsdf.pzgc.special.programme.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.baselibrary.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author lx
 * @创建时间 2019/8/1 0001 14:14
 * @说明
 **/

public class ProgrammeListModel extends BaseViewModel {

    private MutableLiveData<List<String>> data;
    private List<String> list;

    public MutableLiveData<List<String>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        requset();
        return data;
    }

    private void requset() {
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        data.setValue(list);
    }
}
