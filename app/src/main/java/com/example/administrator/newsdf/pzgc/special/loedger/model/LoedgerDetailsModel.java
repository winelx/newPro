package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailRecord;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.DetailsOption;

import java.util.ArrayList;
import java.util.List;

public class LoedgerDetailsModel extends ViewModel {
    private MutableLiveData<List<Object>> data;
    private List<Object> list;

    public MutableLiveData<List<Object>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        request();
        return data;
    }

    private void request() {
        list.add(new DetailsOption(""));
        list.add(new DetailsOption(""));
        list.add("处理记录");
        list.add(new DetailRecord(""));
        list.add(new DetailRecord(""));
        list.add(new DetailRecord(""));
        data.setValue(list);
    }
}
