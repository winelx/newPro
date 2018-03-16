package com.example.administrator.newsdf.adapter;

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
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.WebActivity;
import com.example.administrator.newsdf.photopicker.PhotoPreview;
import com.example.administrator.newsdf.utils.Dates;

import java.util.ArrayList;


/**
 * description:
 *
 * @author winelx
 *         date:2017/11/30 0030:下午 14:46
 *         update: 2018/3/1 0001
 *         version:
 */

public class RectifierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;
    private ArrayList<String> Title;
    private Dates dates = new Dates();

    public RectifierAdapter(Context mContext, ArrayList<String> listA, ArrayList<String> Title) {
        this.mContext = mContext;
        this.mData = listA;
        this.Title = Title;
    }

    //初始化布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rectifier_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeHolder) {
            bindView((TypeHolder) holder, position);
        }
    }

    private void bindView(final TypeHolder holder, final int position) {

        String imgUrl = mData.get(position);
        //拿到.位置
        int doc = imgUrl.lastIndexOf(".");
        //截取doc+1后面的字符串，包括doc+1；
        String strs = imgUrl.substring(doc + 1);
        if (strs.equals("pdf")) {
            holder.img.setVisibility(View.GONE);
            holder.audio_relat.setVisibility(View.VISIBLE);
            //背景色
            holder.audio_relat.setBackgroundColor(Color.parseColor("#f8f5f6"));

            holder.audio_relat_name.setText(Title.get(position) + ".pdf");
            holder.audio_relat_icon.setText("P");
            //字体背景色
            holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#e98e90"));
            holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (strs.equals("doc") || strs.equals("docx")) {
            holder.img.setVisibility(View.GONE);
            holder.audio_relat.setVisibility(View.VISIBLE);
            holder.audio_relat_name.setText(Title.get(position) + ".doc");
            holder.audio_relat.setBackgroundColor(Color.parseColor("#f1f6f7"));
            holder.audio_relat_icon.setText("W");
            holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#5e8ed3"));
        } else if (strs.equals("doc") || strs.equals("docx")) {
            holder.img.setVisibility(View.GONE);
            holder.audio_relat.setVisibility(View.VISIBLE);
            holder.audio_relat_name.setText(Title.get(position) + ".xsl");
            holder.audio_relat.setBackgroundColor(Color.parseColor("#f5f8f7"));
            holder.audio_relat_icon.setText("X");
            holder.audio_relat_icon.setTextColor(Color.parseColor("#FFFFFF"));
            holder.audio_relat_icon.setBackgroundColor(Color.parseColor("#67cf95"));
        } else {
            holder.audio_relat.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(mData.get(position))
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
                    String urlpath = mData.get(i);
                    int doc = urlpath.lastIndexOf(".");
                    //截取doc+1后面的字符串，包括doc+1；
                    String strs = urlpath.substring(doc + 1);
                    if (strs.equals("jpg")) {
                        path.add(mData.get(i));
                    }
                }
                PhotoPreview.builder().setPhotos(path).setCurrentItem(position).
                        setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(imagepath)
                        .start((Activity) mContext);
            }
        });
        holder.audio_relat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("http", mData.get(position));
                mContext.startActivity(intent);
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

    public void getData(ArrayList<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
