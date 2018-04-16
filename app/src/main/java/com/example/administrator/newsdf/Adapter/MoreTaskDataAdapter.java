package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;

import java.util.ArrayList;

/**
 * head_for_recyclerview   item_for_recycler_view   foot_for_recyclerview
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

    public boolean isHead(int position) {
        return HEAD_COUNT != 0 && position == 0;
    }

    public boolean isFoot(int position) {
        return FOOT_COUNT != 0 && position == getContentSize() + HEAD_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if (HEAD_COUNT != 0 && position == 0) {
            // 头部
            return TYPE_HEAD;
        } else if (FOOT_COUNT != 0 && position == HEAD_COUNT + contentSize) {
            // 尾部
            return TYPE_FOOTER;
        } else {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MoreTaskDataAdapter.HeadHolder) {
            // 头部
            MoreTaskDataAdapter.HeadHolder myHolders = (MoreTaskDataAdapter.HeadHolder) holder;
            if (list.size() != 0) {
                myHolders.button.setVisibility(View.VISIBLE);
            } else {
                myHolders.button.setVisibility(View.GONE);
            }
        } else if (holder instanceof MoreTaskDataAdapter.ContentHolder) {
            // 内容
            MoreTaskDataAdapter.ContentHolder myHolder = (MoreTaskDataAdapter.ContentHolder) holder;
            myHolder.itemText.setText(list.get(position - 1));
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
                    ToastUtils.showLongToast("新增");
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
        private TextView button;

        public HeadHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.head);
        }
    }

    // 内容
    private class ContentHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        public ContentHolder(View itemView) {
            super(itemView);
            itemText =itemView.findViewById(R.id.item_text);
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