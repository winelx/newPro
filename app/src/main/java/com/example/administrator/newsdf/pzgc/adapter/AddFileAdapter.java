package com.example.administrator.newsdf.pzgc.adapter;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.RoundImageView;
import com.example.baselibrary.bean.FileTypeBean;
import com.example.baselibrary.ui.utils.PdfPreview;

import java.util.ArrayList;

public class AddFileAdapter extends RecyclerView.Adapter<AddFileAdapter.FileViewHolder> {

    private ArrayList<FileTypeBean> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    final static int MAX = 100;
    private boolean lean = true;

    public AddFileAdapter(Context mContext, ArrayList<FileTypeBean> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            //控制删除按钮
            if (lean) {
                holder.vSelected.setVisibility(View.VISIBLE);
            } else {
                holder.vSelected.setVisibility(View.GONE);
            }
            FileTypeBean bean = photoPaths.get(position);
            if (bean.getFilepath().contains(".pdf") || bean.getFilepath().contains(".PDF")) {
                holder.ivPhoto.setVisibility(View.GONE);
                holder.audio_relat.setVisibility(View.VISIBLE);
                //背景色
                holder.audio_relat.setBackgroundColor(Color.parseColor("#f8f5f6"));
                holder.audio_relat_name.setText(bean.getFilename());
                holder.audio_relat_icon.setText("P");
                //字体背景色
                holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#e98e90"));
                holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
            } else if (bean.getFilepath().contains(".doc") || bean.getFilepath().contains(".docx")) {
                holder.ivPhoto.setVisibility(View.GONE);
                holder.audio_relat.setVisibility(View.VISIBLE);
                holder.audio_relat_name.setText(bean.getFilename());
                holder.audio_relat.setBackgroundColor(Color.parseColor("#f1f6f7"));
                holder.audio_relat_icon.setText("W");
                holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
                holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#5e8ed3"));
            } else if (bean.getFilepath().contains(".xls") || bean.getFilepath().contains(".xlsx")) {
                holder.ivPhoto.setVisibility(View.GONE);
                holder.audio_relat.setVisibility(View.VISIBLE);
                holder.audio_relat_name.setText(bean.getFilename());
                holder.audio_relat.setBackgroundColor(Color.parseColor("#f5f8f7"));
                holder.audio_relat_icon.setText("X");
                holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
                holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#67cf95"));
            } else if (bean.getFilepath().contains(".dwg")) {
                holder.ivPhoto.setVisibility(View.GONE);
                holder.audio_relat.setVisibility(View.VISIBLE);
                //背景色
                holder.audio_relat.setBackgroundColor(Color.parseColor("#f5f6f8"));
                holder.audio_relat_name.setText(bean.getFilename());
                holder.audio_relat_icon.setText("D");
                //字体背景色
                holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#3453d5"));
                holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
                holder.tips.setText("点击下载");
            } else {
                //加载图片
                RequestOptions options = new RequestOptions()
                        //图片加载出来前，显示的图片
                        .placeholder(R.mipmap.image_loading)
                        //url为空的时候,显示的图片
                        .fallback(R.mipmap.image_error)
                        //图片加载失败后，显示的图片
                        .error(R.mipmap.image_error);
                Glide.with(mContext)
                        .load(bean.getFilepath())
                        .apply(options)
                        .thumbnail(0.1f)
                        .into(holder.ivPhoto);
            }
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
                    ArrayList<String> imagepaths = new ArrayList<>();
                    int page = 0;
                    //获取图片地址集合
                    for (int i = 0; i < photoPaths.size(); i++) {
                        imagepaths.add(photoPaths.get(i).getFilepath());
                    }
                    for (int i = 0; i < imagepaths.size(); i++) {
                        String path = imagepaths.get(i);
                        if (path.equals(bean.getFilepath())) {
                            page = i;
                        }
                    }
                    //查看图片
                    PhotoPreview.builder()
                            //图片路径
                            .setPhotos(imagepaths)
                            //图片位置
                            .setCurrentItem(page)
                            //删除按钮
                            .setShowDeleteButton(false)
                            //下载按钮
                            .setShowUpLoadeButton(false)
                            // 图片名称
                            .setImagePath(new ArrayList<String>())
                            .start((Activity) mContext);
                }
            });
            holder.audio_relat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PdfPreview.builder().setPdfUrl(bean.getFilepath()).start((Activity) mContext);
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

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView ivPhoto;
        private ImageView vSelected;
        private ImageView img_add;
        RelativeLayout audio_relat;
        TextView audio_relat_name, audio_relat_icon, tips;

        public FileViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            img_add = itemView.findViewById(R.id.bt_add);
            audio_relat = itemView.findViewById(R.id.audio_relat);
            tips = itemView.findViewById(R.id.tips);
            audio_relat_name = itemView.findViewById(R.id.audio_relat_name);
            audio_relat_icon = itemView.findViewById(R.id.audio_relat_icon);
        }
    }

    public void getData(ArrayList<FileTypeBean> photoPaths) {
        this.photoPaths = photoPaths;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void addlick(View view, int position);

        void delete(int position);

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
