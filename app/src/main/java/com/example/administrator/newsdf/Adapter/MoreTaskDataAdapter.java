package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.MoretaskActivity;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;

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
    private ArrayList<String> list;
    private Context mContext;

    public MoreTaskDataAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        if (holder instanceof MoreTaskDataAdapter.HeadHolder) {
            // 头部
            MoreTaskDataAdapter.HeadHolder myHolders = (MoreTaskDataAdapter.HeadHolder) holder;
            if (list.size() != 0) {
                myHolders.button.setVisibility(View.VISIBLE);
            } else {
                myHolders.button.setVisibility(View.GONE);
            }
            myHolders.taskNumber.setText("(" + list.size() + ")");
        } else if (holder instanceof MoreTaskDataAdapter.ContentHolder) {
            // 内容
            final MoreTaskDataAdapter.ContentHolder myHolder = (MoreTaskDataAdapter.ContentHolder) holder;
            myHolder.itemText.setText(list.get(position - 1));
            myHolder.itemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoretaskActivity activity = (MoretaskActivity) mContext;
                    activity.onclick(position - 1);
                }
            });
        } else { // 尾部
            MoreTaskDataAdapter.FootHolder myHoldered = (MoreTaskDataAdapter.FootHolder) holder;
            if (list.size() != 0) {
                myHoldered.footer.setVisibility(View.VISIBLE);
            } else {
                myHoldered.footer.setVisibility(View.GONE);
            }
            myHoldered.footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(mContext, DirectlyreplyActivity.class);
                    intent.putExtra("id","");
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + HEAD_COUNT + FOOT_COUNT;
    }

    // 头部
    private class HeadHolder extends RecyclerView.ViewHolder {
        private TextView button, taskNumber;

        public HeadHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.head);
            taskNumber = itemView.findViewById(R.id.taskNumber);
        }
    }

    // 内容
    private class ContentHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        public ContentHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
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

    public void getData(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}