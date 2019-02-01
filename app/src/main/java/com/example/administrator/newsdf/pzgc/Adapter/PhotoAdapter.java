package com.example.administrator.newsdf.pzgc.Adapter;


import android.annotation.SuppressLint;
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
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckRectificationActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReplyActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckValidationActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckitemActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckmassageActivity;
import com.example.administrator.newsdf.pzgc.activity.home.same.ReplyActivity;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.RoundImageView;

import java.util.ArrayList;


/**
 * Created by donglua on 15/5/31.
 * 添加图片
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    final static int MAX = 100;
    private String type;
    private boolean lean = true;

    public PhotoAdapter(Context mContext, ArrayList<String> photoPaths, String tag) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.type = tag;
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
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除本地图片
                    FileUtils.deleteFile(photoPaths.get(position));
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
                    ArrayList<String> imagepath = new ArrayList<String>();
                    paths.addAll(photoPaths);
                    PhotoPreview.builder().setPhotos(paths).setCurrentItem(position).setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(imagepath)
                            .start((Activity) mContext);
                }
            });

        } else {
            //添加照片
            holder.img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (type) {
                        case "Reply":
                            ReplyActivity reply = (ReplyActivity) mContext;
                            //调用相机
                            reply.Cream();
                            break;
                        case "Check":
                            CheckitemActivity Checkitem = (CheckitemActivity) mContext;
                            //调用相机
                            Checkitem.showPopwindow();
                            break;
                        case "Message":
                            CheckmassageActivity message = (CheckmassageActivity) mContext;
                            //调用相机
                            message.showPopwindow();
                            break;
                        case "Rectifi":
                            CheckRectificationActivity Rectifi = (CheckRectificationActivity) mContext;
                            //调用相机
                            Rectifi.showPopwindow();
                            break;
                        case "CheckReply":
                            CheckReplyActivity CheckReply = (CheckReplyActivity) mContext;
                            //调用相机
                            CheckReply.showPopwindow();
                            break;
                        case "validation":
                            CheckValidationActivity validation = (CheckValidationActivity) mContext;
                            //调用相机
                            validation.showPopwindow();
                            break;
                        case "other":
                            mOnItemClickListener.addlick(v, position);
                            break;
                        default:

                            break;
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

    public void getData(ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void addlick(View view, int position);
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