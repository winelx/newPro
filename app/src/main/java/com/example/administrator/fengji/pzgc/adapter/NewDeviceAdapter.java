package com.example.administrator.fengji.pzgc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.bean.DetailsBean;


import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/30 0030.
 * @description: 新增特种设备整改适配器
 * @Activity NewDeviceActivity
 */

public class NewDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<DetailsBean> list;

    public NewDeviceAdapter(Context mContext, ArrayList<DetailsBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_newdevice, parent, false);
        return new NewDeviceAdapter.Viewholder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof Viewholder) {
            ((Viewholder) holder).newinspectorg.setText(list.get(position).getCisName());
            ((Viewholder) holder).problem.setText("第" + (position + 1) + "项问题");
            ((Viewholder) holder).newinspectorg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onclick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView newinspectorg, problem;

        Viewholder(View itemView) {
            super(itemView);
            newinspectorg = itemView.findViewById(R.id.new_inspect_org);
            problem = itemView.findViewById(R.id.problem);
        }
    }

    public void setNewDate(ArrayList<DetailsBean> mData) {
        this.list = mData;
        notifyDataSetChanged();
    }


    public interface OnClickListener {
        /**
         * 点击事件
         *
         * @param view
         * @param position
         */
        void onclick(View view, int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
