package com.example.zcjlmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zcjlmodule.R;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.bean.ExamineBean;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class ExamineDismantlingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ExamineBean> list;
    private Context mContext;

    public ExamineDismantlingAdapter(ArrayList<ExamineBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Viewholder vh = new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_examine_zc, parent, false));
        //将创建的View注册点击事件
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Viewholder && list.size() > 0) {
            bindGrid((Viewholder) holder, position);
        }
    }

    private void bindGrid(Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class Viewholder extends RecyclerView.ViewHolder {

        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}
