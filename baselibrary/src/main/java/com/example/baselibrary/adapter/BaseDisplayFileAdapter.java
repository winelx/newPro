package com.example.baselibrary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.baselibrary.R;
import com.example.baselibrary.bean.FileTypeBean;

import java.util.ArrayList;

/**
 * @author lx
 * 创建日期：2019/3/9 0009
 * 描述：基本的文件展示类，使用实体收数据
 * {@link }
 */
public class BaseDisplayFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FileTypeBean> list;
    private Context mContext;

    public BaseDisplayFileAdapter(Context mContext, ArrayList<FileTypeBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new typeholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.base_photoitem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof typeholder) {
            bindView((typeholder) holder, position, list.get(position));
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindView(typeholder holder, int position, FileTypeBean bean) {
        if ("pdf".equals(bean.getFileext())) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            //背景色
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f8f5f6"));
            holder.audioRelatName.setText(bean.getFilename() + ".pdf");
            holder.audioRelatIcon.setText("P");
            //字体背景色
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#e98e90"));
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
        } else if ("doc".equals(bean.getFileext()) || "docx".equals(bean.getFileext())) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            holder.audioRelatName.setText(bean.getFilename() + ".doc");
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f1f6f7"));
            holder.audioRelatIcon.setText("W");
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#5e8ed3"));
        } else if ("xls".equals(bean.getFileext()) || "xlsx".equals(bean.getFileext())) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            holder.audioRelatName.setText(bean.getFilename() + ".xsl");
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f5f8f7"));
            holder.audioRelatIcon.setText("X");
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#67cf95"));
        } else if ("txt".equals(bean.getFileext())) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            holder.audioRelatName.setText(bean.getFilename() + ".txt");
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f5f8f7"));
            holder.audioRelatIcon.setText("T");
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#67cf95"));
        } else {
            holder.audioRelat.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            //是否显示显示为缩略图
            RequestOptions options = new RequestOptions();
            options.centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.mipmap.__picker_ic_photo_black_48dp)
                    .error(R.mipmap.image_error);
            Glide.with(mContext)
                    .load(bean.getFilepath())
                    .apply(options)
                    .thumbnail(Glide.with(mContext)
                            .load(R.mipmap.image_loading))
                    .into(holder.img);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看图片
                ArrayList<String> imagepath = new ArrayList<String>();
                ArrayList<String> path = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    String strs = list.get(position).getFileext();
                    //图片可能为jpg 也可能是png
                    if ("txt".equals(strs) || "xls".equals(strs) || "xlsx".equals(strs) || "pdf".equals(strs) || "PNG".equals(strs) || "doc".equals(strs) || "docx".equals(strs) || "dwg".equals(strs)) {
                    } else {
                        path.add(list.get(i).getFilepath());
                    }
                }
                String str = list.get(position).getFilepath();
                int pos = 0;
                for (int i = 0; i < path.size(); i++) {
                    String str1 = path.get(i);
                    if (str.equals(str1)) {
                        pos = i;
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class typeholder extends RecyclerView.ViewHolder {
        ImageView img;
        RelativeLayout audioRelat;
        TextView audioRelatName, audioRelatIcon;

        public typeholder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgage);
            audioRelat = itemView.findViewById(R.id.audio_relat);
            audioRelatName = itemView.findViewById(R.id.audio_relat_name);
            audioRelatIcon = itemView.findViewById(R.id.audio_relat_icon);
        }
    }

    public void setNewData(ArrayList<FileTypeBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
