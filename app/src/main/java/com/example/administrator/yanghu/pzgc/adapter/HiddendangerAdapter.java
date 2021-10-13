package com.example.administrator.yanghu.pzgc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.bean.GradeRecyclerAdapter;
import com.example.administrator.yanghu.pzgc.bean.HiddendangerBean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/10 0010.
 * @description:
 * @Activity：
 */

public class HiddendangerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<HiddendangerBean> list;

    public HiddendangerAdapter(android.content.Context mContext, ArrayList<HiddendangerBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_category_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HiddendangerAdapter.ViewHolder) {
            ((HiddendangerAdapter.ViewHolder) holder).pop_task_item.setText(list.get(position).getLabel());
            ((HiddendangerAdapter.ViewHolder) holder).pop_task_item.setOnClickListener(new View.OnClickListener() {
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView pop_task_item;

        public ViewHolder(View itemView) {
            super(itemView);
            pop_task_item = itemView.findViewById(R.id.category_content);
        }
    }

    public interface onClickListener {
        void onclick(View v, int position);
    }

    private GradeRecyclerAdapter.onClickListener onClickListener;

    public void setOnClickListener(GradeRecyclerAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void getData(ArrayList<HiddendangerBean> data) {
        this.list = data;
        notifyDataSetChanged();
    }
}
