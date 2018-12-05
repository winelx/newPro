package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SeeDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.bean.SeeDetailsReply;
import com.example.administrator.newsdf.pzgc.bean.SeeDetailsTop;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:查看整改问题项详情界面
 */

public class SeeDetailsActivity extends BaseActivity {
    private SeeDetailsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        list = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        mRecyclerView.addItemDecoration(divider1);
        ArrayList<FileTypeBean> image = new ArrayList<>();
        image.add(new FileTypeBean("测试图片.jpg", "http://file06.16sucai.com/2016/0324/7b07c97b5e653c45c37499236848d519.jpg", "jpg"));
        image.add(new FileTypeBean("测试图片.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543552318747&di=78bc8db674e2ddac404e03f0d53a98c6&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd009b3de9c82d158ec9917f38d0a19d8bc3e425c.jpg", "jpg"));
        image.add(new FileTypeBean("测试图片.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543552346656&di=bc349a93fcdced135869fae8d4384e23&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd439b6003af33a8724e4b645cb5c10385243b5e0.jpg", "jpg"));
        list.add(new SeeDetailsTop("ce", image));
        list.add(new SeeDetailsReply("ce", image));
        list.add(new SeeDetailsTop("ce", image));
        list.add(new SeeDetailsReply("ce", image));
        list.add(new SeeDetailsTop("ce", image));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setAdapter(new SeeDetailsAdapter(mContext, list));
    }
}
