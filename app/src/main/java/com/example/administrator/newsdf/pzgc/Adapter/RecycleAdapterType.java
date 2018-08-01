package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/1 0001.
 */

public class RecycleAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> list;

    public RecycleAdapterType(Context mContext,ArrayList<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclertype, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewholder && list.size()>0) {
            bindGrid((ItemViewholder) holder, position);
        }
    }

    private void bindGrid(ItemViewholder holder, int position) {
        LogUtil.d("list",list.size());
        if (list.size() != 0) {
            if (position < 3) {
                try {
                    String str = list.get(position);
                    if (str.contains("pdf")) {
                        holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                        holder.imageView.setVisibility(View.GONE);
                        holder.attachment.setVisibility(View.VISIBLE);
                        holder.content.setText(list.get(position));
                        //pdf的icon显示图
                        holder.tag.setText("P");
                        //设置文档布局背景色
                        holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                        //设置文件名
                        holder.content.setText(list.get(position));
                        //设置文字的颜色
                        holder.tag.setTextColor(Color.parseColor("#FFFFFF"));
                        //设置文字背景色
                        holder.tag.setBackgroundColor(Color.parseColor("#e98e90"));
                    } else if (str.contains("doc") || str.contains("docx")) {
                        holder.imageView.setVisibility(View.GONE);
                        holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                        holder.attachment.setVisibility(View.VISIBLE);
                        holder.content.setText(list.get(position));
                        //设置文档布局背景色
                        holder.attachment.setBackgroundColor(Color.parseColor("#f1f6f7"));
                        //pdf的icon显示图
                        holder.tag.setText("W");
                        //设置文件名
                        holder.content.setText(list.get(position));
                        //设置文字的颜色
                        holder.tag.setTextColor(Color.parseColor("#FFFFFF"));
                        //设置文字背景色
                        holder.tag.setBackgroundColor(Color.parseColor("#5e8ed3"));
                    } else if (str.contains("xlsx") || str.contains("xls")) {
                        holder.imageView.setVisibility(View.GONE);
                        holder.attachment.setVisibility(View.VISIBLE);
                        holder.content.setText(list.get(position));
                        holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                        holder.tag.setText("X");
                    } else {
                        holder.imageView.setVisibility(View.VISIBLE);
                        holder.attachment.setVisibility(View.GONE);
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .dontAnimate()
                                .error(R.mipmap.image_loading)
                                .placeholder(R.mipmap.image_loading)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(mContext)
                                .load(str)
                                .apply(options)
                                .transition(new DrawableTransitionOptions().crossFade(1000))
                                .into(holder.imageView);
                    }
                } catch (IndexOutOfBoundsException e) {

                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private class ItemViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private RelativeLayout attachment;
        private TextView content;
        private TextView tag;

        public ItemViewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.infaimage);
            attachment = itemView.findViewById(R.id.attachment);
            content = itemView.findViewById(R.id.content);
            tag = itemView.findViewById(R.id.tag);
        }
    }

}
