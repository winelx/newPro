package com.example.administrator.fengji.pzgc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.Dates;

import java.util.ArrayList;

/**
 * 任务详情回复时展示图片的recyclervuiew的适配器
 * Created by Administrator on 2018/3/12 0012.
 */

public class DialogRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;
    private boolean blean;

    public DialogRecAdapter(Context mContext, ArrayList<String> listA) {
        this.mContext = mContext;
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

        holder.error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.deleteFile(mData.get(position));
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PhotoPreview.builder().setPhotos(paths).setCurrentItem(position).
//                        setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(paths)
//                        .start((Activity) mContext);
//            }
//        }); holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PhotoPreview.builder().setPhotos(paths).setCurrentItem(position).
//                        setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(paths)
//                        .start((Activity) mContext);
//            }
//        });
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

    public void getData(ArrayList<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
