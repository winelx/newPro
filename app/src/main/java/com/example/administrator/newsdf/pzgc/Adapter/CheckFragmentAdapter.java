package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/2 0002.
 */

public class CheckFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Checkbean> list;
    public CheckFragmentAdapter(Context mContext, ArrayList<Checkbean> list) {
        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_check_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder && list.size() > 0) {
            bindBAnner((ViewHolder) holder, position);
        }
    }

    private void bindBAnner(final ViewHolder holder, int position) {
        holder.CheckName.setText(list.get(position).getName());
        Glide.with(mContext)
                .load(list.get(position).getPath())
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .into(holder.checkImage);

        holder.checklear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1
                int position = holder.getLayoutPosition();
                // 2
                mOnItemClickListener.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView CheckName;
        private ImageView checkImage;
        private LinearLayout checklear;
        public ViewHolder(View itemView) {
            super(itemView);
            checkImage = itemView.findViewById(R.id.checkImage);
            CheckName = itemView.findViewById(R.id.CheckName);
            checklear=itemView.findViewById(R.id.checklear);
        }
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
