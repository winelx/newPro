package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.home.AllListmessageActivity;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.Inface_all_item;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CollectionlistActivity;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CommentmessageActivity;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * description: 全部任务界面列表
 *
 * @author lx
 *         date: 2018/8/16 0016 上午 10:15
 *         update: 2018/8/16 0016
 *         version:
 */
public class AllTaskListItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Inface_all_item> list;
    private Context mContext;
    private RecycleAdapterType adapterType;
    private String status;

    public AllTaskListItem(ArrayList<Inface_all_item> list, Context mContext, String status) {
        this.list = list;
        this.mContext = mContext;
        this.status = status;
    }

    public AllTaskListItem() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alltasklistitem, parent, false);
        TypeViewholder vh = new TypeViewholder(view);
        //将创建的View注册点击事件
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeViewholder && list.size() > 0) {
            bindGrid((TypeViewholder) holder, position);
        }
    }

    private void bindGrid(final TypeViewholder holder, final int position) {
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
        holder.comments.setText("(" + list.get(position).getComments() + ")");
        holder.taskcontent.setLayoutManager(new LinearLayoutManager(holder.taskcontent.getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (list.get(position).getUpload().size() > 0) {
            holder.inface_image.setVerticalGravity(View.VISIBLE);
        } else {
            holder.inface_image.setVisibility(View.GONE);
        }
        ArrayList<Audio> path  =new ArrayList<>();
        path = list.get(position).getUpload();
        adapterType = new RecycleAdapterType(holder.taskcontent.getContext(), path, position, status);
        holder.taskcontent.setAdapter(adapterType);
        holder.inter_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1
                int position = holder.getLayoutPosition();
                // 2
                mOnItemClickListener.onItemClick(holder.itemView, position);
            }
        });
        holder.inface_no_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("all".equals(status)) {
                    AllListmessageActivity activity = (AllListmessageActivity) mContext;
                    activity.getumber(position);
                } else if ("message".equals(status)) {
                    CommentmessageActivity activity = (CommentmessageActivity) mContext;
                    activity.getumber(position);
                } else if ("action".equals(status)) {
                    CollectionlistActivity activity = (CollectionlistActivity) mContext;
                    activity.getumber(position);
                }
            }
        });
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position = holder.getLayoutPosition();
                    // 2
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class TypeViewholder extends RecyclerView.ViewHolder {
        TextView interTitle, comments;
        TextView interTime;
        TextView infaceWbsPath;
        TextView interContent;
        TextView infaceUsername;
        TextView infaceUptime;
        TextView infacePcontent;
        TextView infaceLoation;
        RecyclerView taskcontent;
        SlantedTextView inface_item_message;
        RelativeLayout inter_rl;
        NetworkImageView inface_no_image;
        LinearLayout inface_image;

        public TypeViewholder(View itemView) {
            super(itemView);
            comments = itemView.findViewById(R.id.textView4);
            inface_image = itemView.findViewById(R.id.inface_image);
            inface_no_image = itemView.findViewById(R.id.inface_no_image);
            inter_rl = itemView.findViewById(R.id.inter_rl);
            interTitle = itemView.findViewById(R.id.inter_title);
            interTime = itemView.findViewById(R.id.inter_time);
            infaceWbsPath = itemView.findViewById(R.id.inface_wbs_path);
            interContent = itemView.findViewById(R.id.inter_content);
            infaceUsername = itemView.findViewById(R.id.inface_username);
            infaceUptime = itemView.findViewById(R.id.inface_uptime);
            infacePcontent = itemView.findViewById(R.id.inface_pcontent);
            infaceLoation = itemView.findViewById(R.id.inface_loation);
            taskcontent = itemView.findViewById(R.id.taskcontent);
            inface_item_message = itemView.findViewById(R.id.inface_item_message);
        }
    }

    public void getData(ArrayList<Inface_all_item> mdata) {
        this.list = mdata;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
