package com.example.administrator.yanghu.pzgc.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.bean.AduioContent;
import com.example.administrator.yanghu.pzgc.bean.MoretasklistBean;

import java.util.ArrayList;

/**
 * @author lx
 * @date 2018/4/16 0016
 * 多任务上传界面adapter （）
 */

public class MoretaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CONTENT = 0xff01;
    public static final int TYPE_DATA = 0xff02;
    private ArrayList<AduioContent> content;
    private MoreTaskDataAdapter mAdapter;
    private ArrayList<MoretasklistBean> data;
    private Context mContext;
    private String status;

    /**
     * 初始化
     *
     * @param mContext
     */
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
                return new ViewholderData(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_part_item, parent, false));
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
            return TYPE_DATA;
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
            if (content.size() != 0) {
                holder.linearLayout.setVisibility(View.VISIBLE);
//                holder.detailsFixedData.setText(content.get(posotion).getBackdata());
                String stand = content.get(posotion).getCheckStandard();
                    holder.details_checkStandard.setText("标准:" + stand);
                String str = content.get(posotion).getContent();
                holder.detailsContent.setText("内容：" + str);
                //转交人
                holder.detailsUser.setText(content.get(posotion).getLeaderName());
                if (content.get(posotion).getStatus().equals("0")) {
                    status = "2";
                    holder.detailsData.setText(content.get(posotion).getCreateDate() + "  已推送：" + content.get(posotion).getBackdata());
                    //状态
                    holder.detailsBoolean.setText("未完成");
                    //状态
                    holder.detailsBoolean.setTextColor(mContext.getResources().getColor(R.color.Orange));
                } else {
                    status = "1";
                    holder.detailsData.setText(content.get(posotion).getCreateDate());
                    //状态
                    holder.detailsBoolean.setText("已完成");
                    //状态 finish_green
                    holder.detailsBoolean.setTextColor(mContext.getResources().getColor(R.color.finish_green));

                }// 转交说明
//                holder.handoverStatusDescription.setText(content.get(posotion).getCreateDate());
                holder.detailsFixedBoolean.setText("任务状态:");
            }
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
            holder1.dataRec.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mAdapter = new MoreTaskDataAdapter(mContext, status, content.get(0).getLeaderName());
            holder1.dataRec.setAdapter(mAdapter);
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
        TextView detailsTitle, detailsData, details_checkStandard,
                detailsUser, detailsBoolean,
                handoverStatusDescription,
                detailsContent, detailsFixedData, detailsFixedBoolean;

        public ViewholderContent(View itemView) {
            super(itemView);
            detailsFixedBoolean = itemView.findViewById(R.id.details_fixed_boolean);
            details_checkStandard = itemView.findViewById(R.id.details_checkStandard);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            detailsTitle = itemView.findViewById(R.id.details_title);
            detailsData = itemView.findViewById(R.id.details_data);
            detailsUser = itemView.findViewById(R.id.details_user);
            detailsBoolean = itemView.findViewById(R.id.details_boolean);
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
    public void getContent(ArrayList<AduioContent> content, ArrayList<MoretasklistBean> data) {
        this.content = content;
        this.data = data;
        notifyDataSetChanged();
    }


}
