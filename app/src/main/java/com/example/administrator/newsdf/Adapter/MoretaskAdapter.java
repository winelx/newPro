package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public class MoretaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CONTENT = 0xff01;
    public static final int TYPE_DATA = 0xff02;

    private ArrayList<Aduio_content> content;
    private ArrayList<String> data;
    private Context mContext;
    private MoreTaskDataAdapter mAdapter;

    public MoretaskAdapter(Context mContext) {
        this.mContext = mContext;
        content = new ArrayList<>();
        data = new ArrayList<>();
    }

    /**
     * 设置布局
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CONTENT:
                return new ViewholderContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_content, parent, false));
            case TYPE_DATA:
                return new ViewholderData(LayoutInflater.from(parent.getContext()).inflate(R.layout.handover_part_item, parent, false));
            default:
                return null;
        }
    }

    /**
     * 数据分发
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewholderContent && content.size() != 0) {
            bindContent((ViewholderContent) holder, position);
        } else if (holder instanceof ViewholderData && data.size() != 0) {
            bindData((ViewholderData) holder, position);
        }
    }

    /**
     * 获取itemtype类型
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_CONTENT;
        } else if (position == 1) {
            return TYPE_DATA;
        } else {
            return TYPE_CONTENT;
        }
    }

    /**
     * 节点基本信息
     *
     * @param holder
     * @param posotion
     */
    private void bindContent(ViewholderContent holder, int posotion) {
        if (content.size() != 0) {
            holder.linearLayout.setVisibility(View.VISIBLE);
            //标题
            if (content.get(posotion).getName().length() != 0) {
                holder.detailsTitle.setText(content.get(posotion).getName());
            } else {
                holder.detailsTitle.setText("主动上传任务");
            }
            //创建时间
            String data = null;
            try {
                data = Dates.datato(content.get(posotion).getCreateDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.detailsContent.setText(content.get(posotion).getContent());
            holder.detailsFixedData.setText(content.get(posotion).getBackdata());
            //转交人
            holder.detailsUser.setText(content.get(posotion).getLeaderName());
            if (content.get(posotion).getStatus().equals("0")) {
                holder.detailsData.setText(content.get(posotion).getCreateDate() + "  已推送：" + data);
                //状态
                holder.detailsBoolean.setText("未完成");
                //状态
                holder.detailsBoolean.setTextColor(mContext.getResources().getColor(R.color.Orange));

            } else {
                holder.detailsData.setText(content.get(posotion).getCreateDate());
                //状态
                holder.detailsBoolean.setText("已完成");
                //状态 finish_green
                holder.detailsBoolean.setTextColor(mContext.getResources().getColor(R.color.finish_green));

            }// 转交说明
            holder.handoverStatusDescription.setText(content.get(posotion).getCreateDate());
        }
    }

    /**
     * 节点数据
     *
     * @param holder1
     * @param position
     */
    private void bindData(ViewholderData holder1, int position) {
        if (data.size() != 0) {
            holder1.setpin.setVisibility(View.VISIBLE);
            holder1.dataRec.setVisibility(View.VISIBLE);
            holder1.dataRec.setLayoutManager(new LinearLayoutManager(holder1.dataRec.getContext(), LinearLayoutManager.VERTICAL, false));
            mAdapter = new MoreTaskDataAdapter(mContext);
            holder1.dataRec.setAdapter(mAdapter);
            holder1.dataRec.setNestedScrollingEnabled(false);
            mAdapter.getData(data);
        } else {
            holder1.setpin.setVisibility(View.GONE);
            holder1.dataRec.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * 节点基本信息
     */
    private class ViewholderContent extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView detailsTitle, detailsData,
                detailsUser, detailsBoolean,
                handoverStatusDescription,
                detailsContent, detailsFixedData;

        public ViewholderContent(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            detailsTitle = itemView.findViewById(R.id.details_title);
            detailsData = itemView.findViewById(R.id.details_data);
            detailsFixedData = itemView.findViewById(R.id.details_end_data);
            detailsUser = itemView.findViewById(R.id.details_user);
            detailsBoolean = itemView.findViewById(R.id.details_boolean);
            handoverStatusDescription = itemView.findViewById(R.id.handover_status_description);
            detailsContent = itemView.findViewById(R.id.details_content);
        }
    }

    /**
     * /节点数据
     */
    private class ViewholderData extends RecyclerView.ViewHolder {
        RecyclerView dataRec;
        TextView setpin;

        public ViewholderData(View itemView) {
            super(itemView);
            dataRec = (RecyclerView) itemView.findViewById(R.id.handover_part_item);
            setpin = itemView.findViewById(R.id.handovers_text);
        }
    }

    /**
     * @param content 节点基本信息
     * @param data    节点数据
     */
    public void getContent(ArrayList<Aduio_content> content, ArrayList<String> data) {
        this.content = content;
        this.data = data;
        notifyDataSetChanged();
    }


}
