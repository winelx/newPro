package com.example.administrator.newsdf.pzgc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.pzgc.activity.home.same.DirectlyreplysActivity;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.ArrayList;


/**
 * 回复和上传界面图片展示控件
 * Created by Administrator on 2017/12/28 0028.
 */

public class DirectlyreplyAdapter extends RecyclerView.Adapter<DirectlyreplyAdapter.PhotoViewHolder> {
    private ArrayList<String> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;
    private String activity;
    final static int MAX = 9;

    public DirectlyreplyAdapter(Context mContext, ArrayList<String> photoPaths, String activity) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.activity = activity;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.item_add, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.picker_item_photo, parent, false);
                break;
            default:
                break;
        }
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除本地图片

                    Dates.deleteFile(photoPaths.get(position));
                    //删除集合数据
                    photoPaths.remove(position);
                    //刷新界面
                    notifyDataSetChanged();
                }
            });

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> paths = new ArrayList<String>();
                    ArrayList<String> title = new ArrayList<String>();
                    paths.addAll(photoPaths);
                    PhotoPreview.builder().setPhotos(paths).setCurrentItem(position).
                            setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(title)
                            .start((Activity) mContext);
                }
            });
        } else {
            holder.img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity.equals("false")) {
                        DirectlyreplyActivity reply = (DirectlyreplyActivity) mContext;
                        reply.Cream();
                    } else {
                        DirectlyreplysActivity reply = (DirectlyreplysActivity) mContext;
                        reply.Cream();
                    }

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView vSelected;
        private ImageView img_add;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            img_add = itemView.findViewById(R.id.bt_add);
        }
    }

    public void getData(ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        notifyDataSetChanged();
    }
}
