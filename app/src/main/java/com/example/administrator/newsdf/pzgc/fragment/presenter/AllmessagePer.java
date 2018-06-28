package com.example.administrator.newsdf.pzgc.fragment.presenter;

import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.fragment.model.AllMessageModel;
import com.example.administrator.newsdf.pzgc.fragment.model.AllMessageModelImp;
import com.example.administrator.newsdf.pzgc.fragment.view.UiAllMessageView;

import java.util.List;
import java.util.Map;

/**
 * description: presenter层
 *
 * @author lx
 *         date: 2018/6/14 0014 上午 11:01
 *         update: 2018/6/14 0014
 *         version:
 */

public class AllmessagePer {
    //Mode层
    private AllMessageModel messageModel;
    //Ui层
    private UiAllMessageView uiAllMessage;

    //函数
    public AllmessagePer(UiAllMessageView uiAllMessage) {
        super();
        this.uiAllMessage = uiAllMessage;
        this.messageModel = new AllMessageModelImp();
    }

    //获取mode层的数据，并传递给view层
    public void getMode() {
        //显示进度
        //将数据传递到fragment
            messageModel.getData(new AllMessageModel.OnClickListener() {
                @Override
                public void onComple(List<String> list, Map<String, List<Home_item>> map) {
                    uiAllMessage.setAdapter(list, map);
                }
            });

    }
}
