package com.example.administrator.yanghu.pzgc.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yanghu.R;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/7 0007.
 * @description:
 * @Activity：
 */

public class GradeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<SecstandardlistBean>list;

    public GradeRecyclerAdapter(Context mContext, ArrayList<SecstandardlistBean> list) {
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
        if (holder instanceof ViewHolder  ){
            ((ViewHolder) holder).pop_task_item.setText(list.get(position).getCheck_standard());
            ((ViewHolder) holder).pop_task_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onclick(v,position);
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
            pop_task_item=itemView.findViewById(R.id.category_content);
        }
    }
    public interface  onClickListener  {
        void onclick(View v,int position);
    }
    private onClickListener onClickListener;
    public void  setOnClickListener(onClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public  void  getData(ArrayList<SecstandardlistBean>data){
        this.list=data;
        notifyDataSetChanged();
    }
}
