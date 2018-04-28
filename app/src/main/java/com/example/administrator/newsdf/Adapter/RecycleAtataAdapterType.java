package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.AuditparticularsActivity;
import com.example.administrator.newsdf.bean.Aduio_data;
import com.example.administrator.newsdf.utils.CameDialog;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * description:任务详情回复内容适配器
 *
 * @author lx
 *         date:2017/12/13 0013.
 *         update: 2018/2/6 0006
 *         version:
 */

public class RecycleAtataAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private boolean status;
    private ArrayList<Aduio_data> mDatas;
    AuditparticularsActivity activity;
    String str = null;

    public RecycleAtataAdapterType(Context mContext, boolean status) {
        this.mContext = mContext;
        this.status = status;
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.audio_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(final Viewholder holder, final int posotion) {
        if (mDatas.get(posotion).getAttachments().size() != 0) {
            holder.audio_rec.setVisibility(View.VISIBLE);
        } else {
            holder.audio_rec.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(mDatas.get(posotion).getUserpath())
                .into(holder.audio_acathor);
        //上传人
        holder.audio_name.setText(mDatas.get(posotion).getReplyUserName());
        //附加内容
        holder.audio_content.setText(mDatas.get(posotion).getUploadContent());
        //上传时间
        holder.audio_data.setText(mDatas.get(posotion).getUpdateDate());
        //上传地点
        holder.audio_address.setText(mDatas.get(posotion).getUploadAddr());
        //评论条数
        holder.comment_count.setText(mDatas.get(posotion).getCommentCount());
        //评论
        holder.audio_data_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuditparticularsActivity audio = (AuditparticularsActivity) mContext;
                CameDialog.setDialog(audio.getId(), audio);
      //          audio.setDialog();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.audio_rec.setLayoutManager(linearLayoutManager);
        RectifierAdapter adapter = new RectifierAdapter(mContext, mDatas.get(posotion).getAttachments(), mDatas.get(posotion).getFilename());
        holder.audio_rec.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class Viewholder extends RecyclerView.ViewHolder {
        private CircleImageView audio_acathor;
        private TextView audio_name, audio_content, audio_data, audio_address, comment_count;
        private RecyclerView audio_rec;
        private LinearLayout audio_data_comm;

        public Viewholder(View itemView) {
            super(itemView);
            //头像
            audio_acathor = (CircleImageView) itemView.findViewById(R.id.audio_acathor);
            //文字内容
            audio_name = (TextView) itemView.findViewById(R.id.audio_name);
            audio_content = (TextView) itemView.findViewById(R.id.audio_content);
            audio_data = (TextView) itemView.findViewById(R.id.audio_data);
            audio_address = (TextView) itemView.findViewById(R.id.audio_address);
            //图片
            audio_rec = (RecyclerView) itemView.findViewById(R.id.audio_rec);
            //评论
            audio_data_comm = (LinearLayout) itemView.findViewById(R.id.audio_data_comm);
            comment_count = (TextView) itemView.findViewById(R.id.audio_data_commom_count);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


}
