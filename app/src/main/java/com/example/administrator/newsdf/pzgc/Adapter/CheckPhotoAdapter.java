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
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckRectificationActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReplyActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckValidationActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckitemActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckmassageActivity;
import com.example.administrator.newsdf.pzgc.activity.device.ProblemItemActivity;
import com.example.administrator.newsdf.pzgc.activity.home.same.ReplyActivity;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.RoundImageView;

import java.util.ArrayList;


/**
 * Created by donglua on 15/5/31.
 * 添加图片
 */
public class CheckPhotoAdapter extends RecyclerView.Adapter<CheckPhotoAdapter.PhotoViewHolder> {

    private ArrayList<Audio> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;
    boolean learn = false;
    final static int MAX = 100;
    private String status;

    public CheckPhotoAdapter(Context mContext, ArrayList<Audio> photoPaths, String status, boolean learn) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.learn = learn;
        this.status = status;
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
            if (learn) {
                holder.vSelected.setVisibility(View.VISIBLE);
            } else {
                holder.vSelected.setVisibility(View.GONE);
            }
            //加载图片
            Glide.with(mContext)
                    .load(photoPaths.get(position).getName())
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (status) {
                        case "Check":
                            CheckitemActivity Checkitem = (CheckitemActivity) mContext;
                            String status = Checkitem.getstatus();
                            if ("编辑".equals(status)) {
                                ToastUtils.showShortToast("当前不是编辑状态哦");
                            } else {
                                Checkitem.delete(photoPaths.get(position).getContent());
                                //删除本地图片
                                FileUtils.deleteFile(photoPaths.get(position).getName());
                                //删除集合数据
                                photoPaths.remove(position);
                                //刷新界面
                                notifyDataSetChanged();
                            }
                            break;
                        case "Message":
                            CheckmassageActivity message = (CheckmassageActivity) mContext;
                            String str = message.getsttus();
                            if (str.equals("编辑")) {
                                ToastUtils.showShortToast("当前不是编辑状态哦");
                            } else {
                                String strs = photoPaths.get(position).getContent();
                                if (strs.length() > 0) {
                                    message.deleteid(photoPaths.get(position).getContent());
                                }
                                //删除本地图片
                                FileUtils.deleteFile(photoPaths.get(position).getName());
                                //删除集合数据
                                photoPaths.remove(position);
                                //刷新界面
                                notifyDataSetChanged();
                            }

                            break;
                        case "Rectifi":
                            CheckRectificationActivity Rectifi = (CheckRectificationActivity) mContext;
                            String rectifi = Rectifi.getstatus();
                            if ("编辑".equals(rectifi)) {
                                ToastUtils.showShortToast("当前不是编辑状态哦");
                            } else {
                                if (photoPaths.get(position).getContent().length() > 0) {
                                    Rectifi.delete(photoPaths.get(position).getContent());
                                }
                                //删除本地图片
                                FileUtils.deleteFile(photoPaths.get(position).getName());
                                //删除集合数据
                                photoPaths.remove(position);
                                //刷新界面
                                notifyDataSetChanged();
                            }
                            break;
                        case "CheckReply":
                            CheckReplyActivity CheckReply = (CheckReplyActivity) mContext;
                            CheckReply.delete(position);
                            break;
                        case "validation":
                            CheckValidationActivity CheckValida = (CheckValidationActivity) mContext;
                            CheckValida.delete(position);
                            break;
                        default:
                            // 1

                            int position = holder.getLayoutPosition();
                            // 2
                            mOnItemClickListener.deleteClick(holder.itemView, position);
                            break;
                    }
                }
            });
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> paths = new ArrayList<String>();
                    ArrayList<String> imagepath = new ArrayList<String>();
                    for (Audio item : photoPaths) {
                        paths.add(item.getName());
                    }
                    PhotoPreview.builder().setPhotos(paths).setCurrentItem(position).setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(imagepath)
                            .start((Activity) mContext);
                }
            });
        } else {
            //添加照片
            holder.img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (status) {
                        case "Reply":
                            ReplyActivity reply = (ReplyActivity) mContext;
                            //调用相机
                            reply.Cream();
                            break;
                        case "Check":
                            CheckitemActivity Checkitem = (CheckitemActivity) mContext;
                            String ststus = Checkitem.getstatus();
                            if ("编辑".equals(ststus)) {
                                ToastUtils.showShortToast("当前不是编辑状态哦");
                            } else {
                                //调用相机
                                Checkitem.showPopwindow();
                            }
                            break;
                        case "Message":
                            CheckmassageActivity message = (CheckmassageActivity) mContext;
                            String getsttus = message.getsttus();
                            if ("编辑".equals(getsttus)) {
                                ToastUtils.showShortToast("当前不是编辑状态哦");
                            } else {
                                //调用相机
                                message.showPopwindow();
                            }

                            break;
                        case "Rectifi":
                            CheckRectificationActivity Rectifi = (CheckRectificationActivity) mContext;
                            String rectifi = Rectifi.getstatus();
                            if ("编辑".equals(rectifi)) {
                                ToastUtils.showShortToast("当前不是编辑状态哦");
                            } else {
                                //调用相机
                                Rectifi.showPopwindow();
                            }
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
                        default:
                            if (learn) {
                                int position = holder.getLayoutPosition();
                                // 2
                                mOnItemClickListener.addlick(holder.itemView, position);
                            } else {
                                ToastUtils.showLongToast("当前不是编辑状态");
                            }

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

    public void getData(ArrayList<Audio> photoPaths, boolean learn) {
        this.photoPaths = photoPaths;
        this.learn = learn;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void addlick(View view, int position);

        void deleteClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}