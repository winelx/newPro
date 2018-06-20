package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.MoretaskActivity;
import com.example.administrator.newsdf.bean.MoretasklistBean;

import java.util.ArrayList;

/**
 * head_for_recyclerview   item_for_recycler_view   foot_for_recyclerview
 * 多任务回复的适配器
 */

public class MoreTaskDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int HEAD_COUNT = 1;
    private final static int FOOT_COUNT = 1;

    private final static int TYPE_HEAD = 0;
    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;
    private ArrayList<MoretasklistBean> list;
    private Context mContext;
    private String status, usernma;

    public MoreTaskDataAdapter(Context mContext, String status, String username) {
        this.mContext = mContext;
        this.status = status;
        list = new ArrayList<>();
        this.usernma = username;
    }

    public int getContentSize() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if (position == 0) {
            // 头部
            return TYPE_HEAD;
        } else if (position == HEAD_COUNT + contentSize) {
            // 尾部
            return TYPE_FOOTER;
        } else {
            //内容
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.head_for_recyclerview, parent, false);
            return new MoreTaskDataAdapter.HeadHolder(itemView);
        } else if (viewType == TYPE_CONTENT) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_for_recycler_view, parent, false);
            return new MoreTaskDataAdapter.ContentHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.foot_for_recyclerview, parent, false);
            return new MoreTaskDataAdapter.FootHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MoreTaskDataAdapter.HeadHolder) {
            // 头部
            MoreTaskDataAdapter.HeadHolder myHolders = (MoreTaskDataAdapter.HeadHolder) holder;
            myHolders.taskNumber.setText("(" + list.size() + ")");
            myHolders.moretaskname.setText(usernma);

        } else if (holder instanceof MoreTaskDataAdapter.ContentHolder) {
            // 获取holder对象
            MoreTaskDataAdapter.ContentHolder myHolder = (MoreTaskDataAdapter.ContentHolder) holder;

            //设置创建时间
            myHolder.create_time.setText(list.get(position - 1).getUploadTime());
            MoretaskActivity activity = (MoretaskActivity) mContext;
            String taskId = activity.getId();
            String Id = list.get(position - 1).getId();
            if (taskId.equals(Id)) {
                //设置部位名称
                myHolder.itemText.setText(list.get(position - 1).getPartContent());
                myHolder.itemText.setTextColor(Color.parseColor("#f44949"));
            } else {
                //设置部位名称
                myHolder.itemText.setText(list.get(position - 1).getPartContent());
            }

            //设置点击事件
            myHolder.moretask_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoretaskActivity activity = (MoretaskActivity) mContext;
                    activity.onclick(position - 1);
                }
            });

        } else { // 尾部
            MoreTaskDataAdapter.FootHolder myHoldered = (MoreTaskDataAdapter.FootHolder) holder;
            //根据传递的过来的任务状态判断显隐
            myHoldered.footer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + HEAD_COUNT + FOOT_COUNT;
    }

    // 头部
    private class HeadHolder extends RecyclerView.ViewHolder {
        private TextView button, taskNumber, moretaskname;
        private ImageView moretaskimage;

        public HeadHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.head);
            taskNumber = itemView.findViewById(R.id.taskNumber);
            moretaskname = itemView.findViewById(R.id.moretaskname);
            moretaskimage = itemView.findViewById(R.id.moretaskImage);
        }
    }

    // 内容
    private class ContentHolder extends RecyclerView.ViewHolder {
        TextView itemText, create_time;
        RelativeLayout moretask_content;

        public ContentHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
            create_time = itemView.findViewById(R.id.create_time);
            moretask_content = itemView.findViewById(R.id.moretask_content);
        }
    }

    // 尾部
    private class FootHolder extends RecyclerView.ViewHolder {
        private RelativeLayout footer;

        public FootHolder(View itemView) {
            super(itemView);
            footer = itemView.findViewById(R.id.footer);
        }
    }

    public void getData(ArrayList<MoretasklistBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}