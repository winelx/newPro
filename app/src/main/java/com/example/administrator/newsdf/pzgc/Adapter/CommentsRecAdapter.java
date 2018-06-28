package com.example.administrator.newsdf.pzgc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;

import java.util.ArrayList;

/**
 * 评论图片的recyclervuiew的适配器
 * Created by Administrator on 2018/3/12 0012.
 */

public class CommentsRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;
    private ArrayList<String> imgpath;
    private boolean blean;
    private  ArrayList<String> path =new ArrayList<>();
    public CommentsRecAdapter(Context mContext, ArrayList<String> listA,ArrayList<String> imgpath, boolean blean) {
        this.mContext = mContext;
        this.imgpath = imgpath;
        this.mData = listA;
    }

    //初始化布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_pop_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeHolder) {
            bindView((TypeHolder) holder, position);
        }
    }

    private void bindView(final TypeHolder holder, final int position) {
        Glide.with(mContext)
                .load(mData.get(position))
                .into(holder.img);
        if (blean) {
            holder.error.setVisibility(View.VISIBLE);
        } else {
            holder.error.setVisibility(View.GONE);
        }
        holder.error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                FileUtils.deleteFile(mData.get(position));
                notifyDataSetChanged();
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPreview.builder().setPhotos(imgpath).setCurrentItem(position).
                        setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(path)
                        .start((Activity) mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView error;

        public TypeHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.dialog_pop_image);
            error = itemView.findViewById(R.id.dialog_pop_error);
        }
    }

    public void getData(ArrayList<String> mData,ArrayList<String> path) {
        this.mData = mData;
        this.path = path;
        notifyDataSetChanged();
    }

}
