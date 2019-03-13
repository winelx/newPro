package com.example.administrator.newsdf.pzgc.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.RoundImageView;
import com.example.baselibrary.bean.photoBean;

import java.util.ArrayList;

;


/**
 * Created by donglua on 15/5/31.
 * 添加图片
 */
public class BasePhotoAdapter extends RecyclerView.Adapter<BasePhotoAdapter.PhotoViewHolder> {

    private ArrayList<photoBean> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    final static int MAX = 100;
    private boolean lean = true;
    private boolean addstatus = true;

    public BasePhotoAdapter(Context mContext, ArrayList<photoBean> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
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
    public void onBindViewHolder(final PhotoViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            //控制删除按钮
            if (lean) {
                holder.vSelected.setVisibility(View.VISIBLE);
            } else {
                holder.vSelected.setVisibility(View.GONE);
            }
            //加载图片
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.image_loading)//图片加载出来前，显示的图片
                    .fallback(R.mipmap.image_error) //url为空的时候,显示的图片
                    .error(R.mipmap.image_error);//图片加载失败后，显示的图片
            Glide.with(mContext)
                    .load(photoPaths.get(position).getPhotopath())
                    .apply(options)
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除图片
                    mOnItemClickListener.delete(position);
                }
            });
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.seePhoto(position);
                }
            });
        } else {
            if (lean) {
                holder.img_add.setVisibility(View.VISIBLE);
            } else {
                holder.img_add.setVisibility(View.GONE);
            }
            //添加照片
            holder.img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lean) {
                        mOnItemClickListener.addlick(v, position);
                    } else {
                        ToastUtils.showShortToast("当前状态无法添加图片");
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView ivPhoto;
        private ImageView vSelected;
        private ImageView img_add;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            img_add = itemView.findViewById(R.id.bt_add);
        }
    }

    public void getData(ArrayList<photoBean> photoPaths) {
        this.photoPaths = photoPaths;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void addlick(View view, int position);

        void delete(int position);

        void seePhoto(int position);

    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void addview(boolean lean) {
        this.lean = lean;
        notifyDataSetChanged();
    }
}