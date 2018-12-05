package com.example.administrator.newsdf.pzgc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.home.WebActivity;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:文件类型展示
 */

public class FiletypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private ArrayList<FileTypeBean> mData;

    public FiletypeAdapter(Context mContext, ArrayList<FileTypeBean> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    //初始化布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FiletypeAdapter.TypeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rectifier_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FiletypeAdapter.TypeHolder) {
            bindView((FiletypeAdapter.TypeHolder) holder, position);
        }
    }

    private void bindView(final FiletypeAdapter.TypeHolder holder, final int position) {
        String imgUrl = mData.get(position).getName();
        //拿到.位置
        int doc = imgUrl.lastIndexOf(".");
        //截取doc+1后面的字符串，包括doc+1；
        String strs = imgUrl.substring(doc + 1);
        if (strs.equals("pdf")) {
            holder.img.setVisibility(View.GONE);
            holder.audio_relat.setVisibility(View.VISIBLE);
            //背景色
            holder.audio_relat.setBackgroundColor(Color.parseColor("#f8f5f6"));
            holder.audio_relat_name.setText(mData.get(position).getName());
            holder.audio_relat_icon.setText("P");
            //字体背景色
            holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#e98e90"));
            holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
        } else if ("doc".equals(strs) || "docx".equals(strs)) {
            holder.img.setVisibility(View.GONE);
            holder.audio_relat.setVisibility(View.VISIBLE);
            holder.audio_relat_name.setText(mData.get(position).getName());
            holder.audio_relat.setBackgroundColor(Color.parseColor("#f1f6f7"));
            holder.audio_relat_icon.setText("W");
            holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#5e8ed3"));
        } else if ("xls".equals(strs) || "xlsx".equals(strs)) {
            holder.img.setVisibility(View.GONE);
            holder.audio_relat.setVisibility(View.VISIBLE);
            holder.audio_relat_name.setText(mData.get(position).getName());
            holder.audio_relat.setBackgroundColor(Color.parseColor("#f5f8f7"));
            holder.audio_relat_icon.setText("X");
            holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#67cf95"));
        } else {
            holder.audio_relat.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            String urlpath = mData.get(position).getUrl();
//            //截取出后缀
//            String pas = urlpath.substring(urlpath.length() - 4, urlpath.length());
//            //拿到截取后缀后的字段
//            urlpath = urlpath.replace(pas, "");
            //在字段后面添加_min后再拼接后缀
//            urlpath = urlpath + "_min" + pas;
            RequestOptions options = new RequestOptions();
            options.centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.image_error);
            Glide.with(mContext)
                    .load(urlpath)
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
                for (int i = 0; i < mData.size(); i++) {
                    String urlpath = mData.get(i).getName();
                    int doc = urlpath.lastIndexOf(".");
                    //截取doc+1后面的字符串，包括doc+1；
                    String strs = urlpath.substring(doc + 1);
                    //图片可能为jpg 也可能是png
                    if (strs.equals("xls") || strs.equals("xlsx") || strs.equals("pdf") || strs.equals("PNG") || strs.equals("doc") || strs.equals("docx")) {

                    } else {
                        path.add(mData.get(i).getUrl());
                    }
                }
                String str = mData.get(position).getUrl();
                int pos = 0;
                for (int i = 0; i < path.size(); i++) {
                    String str1 = path.get(i);
                    if (str.equals(str1)) {
                        pos = i;
                    }
                }
                PhotoPreview.builder().setPhotos(path).setCurrentItem(pos).
                        setShowDeleteButton(false).setShowUpLoadeButton(true).setImagePath(imagepath)
                        .start((Activity) mContext);
            }
        });
        holder.audio_relat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否是pdf文件
                String imgUrl = mData.get(position).getName();
                //拿到.位置
                int doc = imgUrl.lastIndexOf(".");
                //截取doc+1后面的字符串，包括doc+1；
                String strs = imgUrl.substring(doc + 1);
                if (strs.equals("pdf")) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("http", mData.get(position).getUrl());
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "请到pc端查看详情", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        ImageView img;
        RelativeLayout audio_relat;
        TextView audio_relat_name, audio_relat_icon;

        public TypeHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgage);
            audio_relat = itemView.findViewById(R.id.audio_relat);
            audio_relat_name = itemView.findViewById(R.id.audio_relat_name);
            audio_relat_icon = itemView.findViewById(R.id.audio_relat_icon);
        }
    }

    public void getData(ArrayList<FileTypeBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}