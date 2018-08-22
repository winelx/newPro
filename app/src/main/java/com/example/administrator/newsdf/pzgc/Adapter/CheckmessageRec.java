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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.home.WebActivity;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.ArrayList;

/**
 * Created by 10942 on 2018/8/23 0023.
 */

public class CheckmessageRec extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;
    private ArrayList<String> Title;
    private Dates dates = new Dates();

    public CheckmessageRec(Context mContext, ArrayList<String> listA, ArrayList<String> Title) {
        this.mContext = mContext;
        this.mData = listA;
        this.Title = Title;
    }

    //初始化布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckmessageRec.TypeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_message_rec, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RectifierAdapter.TypeHolder) {
            bindView((RectifierAdapter.TypeHolder) holder, position);
        }
    }

    private void bindView(final RectifierAdapter.TypeHolder holder, final int position) {

            String urlpath = mData.get(position);
            //截取出后缀
            String pas = urlpath.substring(urlpath.length() - 4, urlpath.length());
            //拿到截取后缀后的字段
            urlpath = urlpath.replace(pas, "");
            //在字段后面添加_min后再拼接后缀
            urlpath = urlpath + "_min" + pas;
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

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看图片
                ArrayList<String> imagepath = new ArrayList<String>();
                ArrayList<String> path = new ArrayList<String>();
                PhotoPreview.builder().setPhotos(path).setCurrentItem(position).
                        setShowDeleteButton(false).setShowUpLoadeButton(true).setImagePath(imagepath)
                        .start((Activity) mContext);
            }
        });
        holder.audio_relat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否是pdf文件
                String imgUrl = mData.get(position);
                //拿到.位置
                int doc = imgUrl.lastIndexOf(".");
                //截取doc+1后面的字符串，包括doc+1；
                String strs = imgUrl.substring(doc + 1);
                if (strs.equals("pdf")) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("http", mData.get(position));
                    mContext.startActivity(intent);
                } else {
                    ToastUtils.showLongToast("请到pc端查看详情");
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
        public TypeHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgage);

        }
    }

    public void getData(ArrayList<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
