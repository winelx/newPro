package com.example.administrator.fengji.pzgc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.bean.Makeup;

import java.util.ArrayList;


/**
 * description: 项目成员层级适配器
 *
 * @author lx
 *         date: 2018/3/21 0021 下午 4:06
 *         update: 2018/3/21 0021
 *         version:
 */
public class ProjectmemberAdapter extends RecyclerView.Adapter<ProjectmemberAdapter.PhotoViewHolder> {
    private Context mComtext;
    private ArrayList<Makeup> mData;

    public ProjectmemberAdapter(Context mComtext) {
        this.mComtext = mComtext;
        mData = new ArrayList<>();
    }

    //初始化布局
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pro_member_path, parent, false));

    }

    //绑定数据
    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
      int size=  mData.size();
        if (position+1==size){
            holder.text_item.setText(mData.get(position).getName());
        }else {
            holder.text_item.setText(mData.get(position).getName() + ">>");
        }

        holder.text_item.setTextSize(13);
        holder.text_item.setTextColor(Color.parseColor("#5096F8"));
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position = holder.getLayoutPosition();
                    // 2
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    /**
     * 数据条数
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * viewHolder 复用控件
     */
    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView text_item;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            text_item = itemView.findViewById(R.id.text_item);
        }
    }

    public void getData(ArrayList<Makeup> mDatas) {
        this.mData = mDatas;
        notifyDataSetChanged();
    }


    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
