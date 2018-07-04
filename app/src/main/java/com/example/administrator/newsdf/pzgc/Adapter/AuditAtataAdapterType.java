package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Aduio_data;
import com.example.administrator.newsdf.pzgc.utils.CameDialog;

import java.util.ArrayList;


/**
 * description:任务详情回复内容适配器
 *
 * @author lx
 *         date:2017/12/13 0013.
 *         update: 2018/2/6 0006
 *         version:
 */

public class AuditAtataAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Aduio_data> mDatas;
    String str = null;
    int bright;
    CameDialog cameDialog;

    //判断workfragment是否初始化，如果没有，提亮的时候就不用刷新界面
    public AuditAtataAdapterType(Context mContext, boolean status, int bright) {
        this.mContext = mContext;
        this.bright = bright;
        cameDialog = new CameDialog();
        mDatas = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.audit_content_type, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(final Viewholder holder, final int posotion) {
        Glide.with(mContext)
                .load(mDatas.get(posotion).getUserpath())
                .into(holder.audio_acathor);
        holder.audio_name.setText(mDatas.get(posotion).getReplyUserName());
        holder.audio_data.setText(mDatas.get(posotion).getUpdateDate());
        holder.audio_content.setText(mDatas.get(posotion).getUploadContent());
        holder.audio_address.setText(mDatas.get(posotion).getUploadAddr());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class Viewholder extends RecyclerView.ViewHolder {
        ImageView audio_acathor;
        TextView audio_name, audio_data, audio_content, audio_address;

        Viewholder(View itemView) {
            super(itemView);
            audio_acathor = itemView.findViewById(R.id.audit_acathor);
            audio_name = itemView.findViewById(R.id.audio_name);
            audio_data = itemView.findViewById(R.id.audio_data);
            audio_content = itemView.findViewById(R.id.audio_content);
            audio_address = itemView.findViewById(R.id.audio_address);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


}
