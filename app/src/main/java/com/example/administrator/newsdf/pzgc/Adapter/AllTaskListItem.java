package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Inface_all_item;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;

import java.util.List;

/**
 * Created by Administrator on 2018/8/1 0001.
 */

public class AllTaskListItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Inface_all_item> list;
    private Context mContext;
    private RecycleAdapterType adapterType;

    public AllTaskListItem(List<Inface_all_item> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeViewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.alltasklistitem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindGrid((TypeViewholder) holder, position);
    }

    private void bindGrid(TypeViewholder holder, int position) {
        //标题
        if (list.get(position).getGroupName().length() != 0) {
            holder.interTitle.setText(list.get(position).getGroupName());
        } else {
            holder.interTitle.setText("主动上传任务 ");
        }
        holder.infaceWbsPath.setText(list.get(position).getWbsPath());
        holder.interContent.setText(list.get(position).getContent());
        holder.infaceUsername.setText(list.get(position).getUploador());
        holder.infaceUptime.setText(list.get(position).getUpload_time());
        holder.infaceLoation.setText(list.get(position).getUpload_addr());
        holder.infacePcontent.setText(list.get(position).getUpload_content());
        holder.taskcontent.setLayoutManager(new LinearLayoutManager(holder.taskcontent.getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterType = new RecycleAdapterType(mContext);
        holder.taskcontent.setAdapter(adapterType);
        adapterType.getdata(list.get(position).getFilename());
        switch (list.get(position).getIsFinish() + "") {
            case "0":
                holder.inface_item_message.setTextString("未完成");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.unfinish_gray);
                break;
            case "1":
                holder.inface_item_message.setTextString("已完成");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.finish_green);
                break;
            case "2":
                holder.inface_item_message.setTextString("打回");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.red);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class TypeViewholder extends RecyclerView.ViewHolder {
        TextView interTitle;
        TextView interTime;
        TextView infaceWbsPath;
        TextView interContent;
        TextView infaceUsername;
        TextView infaceUptime;
        TextView infacePcontent;
        TextView infaceLoation;
        TextView infaceImgaeText;
        RecyclerView taskcontent;
        SlantedTextView inface_item_message;

        public TypeViewholder(View itemView) {
            super(itemView);
            interTitle = itemView.findViewById(R.id.inter_title);
            interTime = itemView.findViewById(R.id.inter_time);
            infaceWbsPath = itemView.findViewById(R.id.inface_wbs_path);
            interContent = itemView.findViewById(R.id.inter_content);
            infaceUsername = itemView.findViewById(R.id.inface_username);
            infaceUptime = itemView.findViewById(R.id.inface_uptime);
            infacePcontent = itemView.findViewById(R.id.inface_pcontent);
            infaceLoation = itemView.findViewById(R.id.inface_loation);
            infaceImgaeText = itemView.findViewById(R.id.inface_imgae_text);
            taskcontent = itemView.findViewById(R.id.taskcontent);
            inface_item_message = itemView.findViewById(R.id.inface_item_message);
        }
    }

    public void getData(List<Inface_all_item> mdata) {
        this.list = mdata;
        notifyDataSetChanged();
    }

}
