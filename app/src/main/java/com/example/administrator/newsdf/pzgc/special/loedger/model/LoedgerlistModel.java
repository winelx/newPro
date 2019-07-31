package com.example.administrator.newsdf.pzgc.special.loedger.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerlistActivity;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/7/31 0031 13:46
 * @说明 专项施工台账列表 model类
 * @see LoedgerlistActivity
 **/

public class LoedgerlistModel extends BaseViewModel {
    private MutableLiveData<List<String>> data;
    private List<String> list;

    public MutableLiveData<List<String>> getData(String choice, String id, int page) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        request(choice, id, page);
        return data;
    }

    public void request(String choice, String id, int page) {
        if (page == 1) {
            list.clear();
        }
        if (choice.equals("全部")) {
            for (int i = 0; i < 10; i++) {
                list.add(i + "");
            }
        } else {
            modelinface.onerror();
        }
        data.setValue(list);
    }


}
