package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.MoretaskActivity;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.bean.MoretaskBean;
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
    private MoretaskBean moretaskBean;

    public MoreTaskDataAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
        moretaskBean = new MoretaskBean();
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
            if (list.size() != 0) {
                myHolders.button.setVisibility(View.VISIBLE);
            } else {
                myHolders.button.setVisibility(View.GONE);
            }
            myHolders.taskNumber.setText("(" + list.size() + ")");
            myHolders.moretaskname.setText(moretaskBean.getName());
//            myHolders.moretaskimage.setText(moretaskBean.getImage());
        } else if (holder instanceof MoreTaskDataAdapter.ContentHolder) {
            // 获取holder对象
            MoreTaskDataAdapter.ContentHolder myHolder = (MoreTaskDataAdapter.ContentHolder) holder;
            //设置部位名称
            myHolder.itemText.setText(list.get(position - 1).getDetetionName());
            //设置创建时间
            myHolder.create_time.setText(list.get(position - 1).getCreateDate());
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
            if (list.size() != 0) {
                myHoldered.footer.setVisibility(View.VISIBLE);
            } else {
                myHoldered.footer.setVisibility(View.GONE);
            }
            MoretaskActivity moretaskActivity= (MoretaskActivity) mContext;
            if ( moretaskActivity.status!="0"){
                myHoldered.footer.setVisibility(View.GONE);
            }else {
                myHoldered.footer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DirectlyreplyActivity.class);
                        intent.putExtra("id", "");
                        mContext.startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size() + HEAD_COUNT + FOOT_COUNT;
    }

    // 头部
    private class HeadHolder extends RecyclerView.ViewHolder {
        private TextView button, taskNumber, moretaskname ;
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
        TextView itemText,create_time;
        RelativeLayout moretask_content;
        public ContentHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
            create_time = itemView.findViewById(R.id.create_time);
            moretask_content=itemView.findViewById(R.id.moretask_content);
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

    public void getData(ArrayList<MoretasklistBean> list, MoretaskBean moretaskBean) {
        this.list = list;
        this.moretaskBean = moretaskBean;
        notifyDataSetChanged();
    }
}