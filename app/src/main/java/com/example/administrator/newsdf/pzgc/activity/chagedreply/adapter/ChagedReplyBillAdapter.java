package com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：MainActivity
 *{@link }
 */
public class ChagedReplyBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Object> list;
    private Context mContext;

    public ChagedReplyBillAdapter(ArrayList<Object> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
