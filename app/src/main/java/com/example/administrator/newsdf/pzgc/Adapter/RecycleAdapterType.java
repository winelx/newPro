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
import com.example.administrator.newsdf.pzgc.activity.home.AllListmessageActivity;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CollectionlistActivity;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CommentmessageActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/1 0001.
 */

public class RecycleAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Audio> list;
    private int pos;
    String status;
    public RecycleAdapterType(Context mContext, ArrayList<Audio> list,int pos,String status) {
        this.mContext = mContext;
        this.list = list;
        this.pos = pos;
        this.status = status;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclertype, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewholder && list.size() > 0) {
            bindGrid((ItemViewholder) holder, position);
        }
    }

    private void bindGrid(final  ItemViewholder holder, int position) {

        if (list.size() != 0) {
            try {
                String str = list.get(position).getContent();
                if (str.contains("pdf")) {
                    //如果下载路径包含pdf
                    holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                    //隐藏图片
                    holder.imageView.setVisibility(View.GONE);
                    //显示文件控件
                    holder.attachment.setVisibility(View.VISIBLE);
                    holder.content.setText(list.get(position).getName());
                    //pdf的icon显示图
                    holder.tag.setText("P");
                    //设置文档布局背景色
                    holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                    //设置文件名
                    holder.content.setText(list.get(position).getName());
                    //设置文字的颜色
                    holder.tag.setTextColor(Color.parseColor("#FFFFFF"));
                    //设置文字背景色
                    holder.tag.setBackgroundColor(Color.parseColor("#e98e90"));
                    holder.infaceImgaeText.setVisibility(View.GONE);
                } else if (str.contains("doc") || str.contains("docx")) {
                    holder.imageView.setVisibility(View.GONE);
                    holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                    holder.attachment.setVisibility(View.VISIBLE);
                    holder.content.setText(list.get(position).getName());
                    //设置文档布局背景色
                    holder.attachment.setBackgroundColor(Color.parseColor("#f1f6f7"));
                    //pdf的icon显示图
                    holder.tag.setText("W");
                    //设置文件名
                    holder.content.setText(list.get(position).getName());
                    //设置文字的颜色
                    holder.tag.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.infaceImgaeText.setVisibility(View.GONE);
                    //设置文字背景色
                    holder.tag.setBackgroundColor(Color.parseColor("#5e8ed3"));
                } else if (str.contains("xlsx") || str.contains("xls")) {
                    holder.imageView.setVisibility(View.GONE);
                    holder.attachment.setVisibility(View.VISIBLE);
                    holder.content.setText(list.get(position).getName());
                    holder.attachment.setBackgroundColor(Color.parseColor("#f8f5f6"));
                    holder.tag.setText("X");
                    //设置文字的颜色
                    holder.tag.setTextColor(Color.parseColor("#FFFFFF"));
                    //设置文字背景色
                    holder.tag.setBackgroundColor(Color.parseColor("#22c67b"));
                    holder.infaceImgaeText.setVisibility(View.GONE);
                } else {
                    //页面只展示三张图片。当图片超过三招，显示蒙层，展示剩余图片数量
                    if (position == 2) {
                        //计算剩余图片数量
                        int lenght = list.size() - 3;
                        if (lenght > 0) {
                            //展示蒙层
                            holder.infaceImgaeText.setVisibility(View.VISIBLE);
                            //设置展示数字
                            holder.infaceImgaeText.setText(lenght + "+");
                        } else {
                            holder.infaceImgaeText.setVisibility(View.GONE);
                        }
                    } else {
                        //如果图片数量没有超过三张就不显示
                        holder.infaceImgaeText.setVisibility(View.GONE);
                    }
                    //显示图片
                    holder.imageView.setVisibility(View.VISIBLE);
                    //隐藏文件
                    holder.attachment.setVisibility(View.GONE);
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .dontAnimate()
                            .error(R.mipmap.image_loading)//加载失败
                            .placeholder(R.mipmap.image_loading)//加载中
                            .diskCacheStrategy(DiskCacheStrategy.ALL);//全家缓存
                    /**
                     * 图片使用压缩后的，在路径后面拼接_min
                     */
                    //截取出后缀
                    String imgUrl2 = list.get(position).getContent();
                    String pas = imgUrl2.substring(imgUrl2.length() - 4, imgUrl2.length());
                    //拿到截取后缀后的字段
                    imgUrl2 = imgUrl2.replace(pas, "");
                    //在字段后面添加_min后再拼接后缀
                    imgUrl2 = imgUrl2 + "_min" + pas;
                    Glide.with(mContext)
                            .load(imgUrl2)
                            .apply(options)
                            .transition(new DrawableTransitionOptions().crossFade(1000))
                            .into(holder.imageView);

                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            holder.contact_acatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("all".equals(status)){
                        AllListmessageActivity activity = (AllListmessageActivity) mContext;
                        activity.getumber(pos);
                    }else if ("message".equals(status)){
                        CommentmessageActivity activity = (CommentmessageActivity) mContext;
                        activity.getumber(pos);
                    }else if ("action".equals(status)){
                        CollectionlistActivity activity = (CollectionlistActivity) mContext;
                        activity.getumber(pos);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private class ItemViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private RelativeLayout attachment,contact_acatar;
        private TextView content;
        private TextView tag;
        private TextView infaceImgaeText;

        public ItemViewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.infaimage);
            attachment = itemView.findViewById(R.id.attachment);
            contact_acatar = itemView.findViewById(R.id.contact_acatar);
            content = itemView.findViewById(R.id.content);
            tag = itemView.findViewById(R.id.tag);
            infaceImgaeText = itemView.findViewById(R.id.inface_imgaetext);
        }
    }

}
