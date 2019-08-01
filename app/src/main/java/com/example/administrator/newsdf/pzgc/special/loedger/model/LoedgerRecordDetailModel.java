package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoedgerRecordDetailModel extends ViewModel {
    private MutableLiveData<List<String>> data;
    private List<String> list;

    public MutableLiveData<List<String>> getData() {
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
        for (int i = 0; i < 3; i++) {
            list.add(i + "");
        }
        data.setValue(list);
    }
}
