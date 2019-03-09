package com.example.baselibrary.adapter;

import android.annotation.SuppressLint;
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
import com.example.baselibrary.bean.photoBean;

import java.util.ArrayList;

/**
 * @author lx
 * 创建日期：2019/3/9 0009
 * 描述：基本的文件展示类，使用实体收数据
 * {@link }
 */
public class BaseDisplayFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<photoBean> list;
    private Context mContext;
    private boolean compress=true;

    public BaseDisplayFileAdapter( Context mContext,ArrayList<photoBean> list) {
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
    private void bindView(typeholder holder, int position, photoBean bean) {
        String imgUrl;
        try {
            imgUrl = bean.getPhotopath();
        } catch (Exception e) {
            imgUrl = "";
        }
        String filename;
        try {
            filename = bean.getPhotoname();
        } catch (Exception e) {
            filename = "";
        }
        //拿到.位置
        int doc = imgUrl.lastIndexOf(".");
        //截取doc+1后面的字符串，包括doc+1；
        String strs = imgUrl.substring(doc + 1);
        if ("pdf".equals(strs)) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            //背景色
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f8f5f6"));
            holder.audioRelatName.setText(filename + ".pdf");
            holder.audioRelatIcon.setText("P");
            //字体背景色
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#e98e90"));
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
        } else if ("doc".equals(strs) || "docx".equals(strs)) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            holder.audioRelatName.setText(filename + ".doc");
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f1f6f7"));
            holder.audioRelatIcon.setText("W");
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#5e8ed3"));
        } else if ("xls".equals(strs) || "xlsx".equals(strs)) {
            holder.img.setVisibility(View.GONE);
            holder.audioRelat.setVisibility(View.VISIBLE);
            holder.audioRelatName.setText(filename + ".xsl");
            holder.audioRelat.setBackgroundColor(Color.parseColor("#f5f8f7"));
            holder.audioRelatIcon.setText("X");
            holder.audioRelatIcon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audioRelatIcon.setBackgroundColor(Color.parseColor("#67cf95"));
        } else {
            holder.audioRelat.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            //是否显示显示为缩略图
            if (compress) {
                //截取出后缀
                String pas = imgUrl.substring(imgUrl.length() - 4, imgUrl.length());
                //拿到截取后缀后的字段
                imgUrl = imgUrl.replace(pas, "");
                //在字段后面添加_min后再拼接后缀
                imgUrl = imgUrl + "_min" + pas;
            }
            RequestOptions options = new RequestOptions();
            options.centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.mipmap.__picker_ic_photo_black_48dp)
                    .error(R.mipmap.image_error);
            Glide.with(mContext)
                    .load(imgUrl)
                    .apply(options)
                    .thumbnail(Glide.with(mContext)
                            .load(R.mipmap.image_loading))
                    .into(holder.img);
        }
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
}
