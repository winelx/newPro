package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedListActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单列表适配器
 * {@link  ChagedListActivity}
 */
public class ChagedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChagedList> list;
    private Context mContext;

    public ChagedListAdapter(ArrayList<ChagedList> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeContent(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_item_chaged_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeContent) {
            bindcontent((TypeContent) holder, list, position);
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindcontent(final TypeContent holder, ArrayList<ChagedList> list, final int position) {

        holder.chageeListContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.onClick(position);
            }
        });
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipmenulayout.quickClose();
                mOnSwipeListener.onDel(position, holder.swipmenulayout);
            }
        });
        ChagedList chagedList = list.get(position);
        if (chagedList.getCode() != null) {
            holder.itemNumber.setText("编号：" + chagedList.getCode());
        } else {
            holder.itemNumber.setText("编号：");
        }
        if (chagedList.getSendUserName() != null) {
            holder.releasePeople.setText("下发人：" + chagedList.getSendUserName());
        } else {
            holder.releasePeople.setText("下发人：");
        }

        if (chagedList.getSend_date() != null) {
            holder.releaseData.setText("下发日期：" + chagedList.getSend_date().substring(0, 10));
        } else {
            holder.releaseData.setText("下发日期：");
        }

        if (chagedList.getSorgName() != null) {
            holder.chagedNoticeOrgname.setText("整改组织：" + chagedList.getSorgName());
        } else {
            holder.chagedNoticeOrgname.setText("整改组织：");
        }
        if (chagedList.getAuserName() != null) {
            holder.auserName.setText("下节点负责人：" + chagedList.getAuserName());
        } else {
            holder.auserName.setText("下节点负责人：");
        }
        if (chagedList.getRuserName() != null) {
            holder.ruserName.setText("整改负责人：" + chagedList.getRuserName());
        } else {
            holder.ruserName.setText("整改负责人：");
        }
        /*通知单完成数*/
        int noticeFinishCount = chagedList.getNoticeFinishCount();
        /*总下发通知单数*/
        int noticeCount = chagedList.getNoticeCount();
        String leanht = noticeCount + "";
        holder.noticefinishcount.setText(Dates.setText(mContext, "完成比例：" + noticeFinishCount + "/" + noticeCount, 4 + leanht.length(), R.color.finish_green));

        int status = chagedList.getStatus();
//        0:未下发；1：已下发;2:回复中；3：完成；20：未处理；30：已处理
        holder.swipmenulayout.setIos(true).setLeftSwipe(false);
        switch (status) {
            case 0:
                holder.infaceItemMessage.setTextString("保存");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.unfinish_gray);
                holder.swipmenulayout.setIos(true).setLeftSwipe(true);
                break;
            case 1:
                holder.infaceItemMessage.setTextString("已下发");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.unfinish_gray);
                break;
            case 2:
                holder.infaceItemMessage.setTextString("回复中");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.Orange);
                break;
            case 3:
                holder.infaceItemMessage.setTextString("已完成");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);
                break;
            case 20:
                holder.infaceItemMessage.setTextString("未处理");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.Orange);
                break;
            case 30:
                holder.infaceItemMessage.setTextString("已处理");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);
                break;
            default:
                break;
        }


    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    class TypeContent extends RecyclerView.ViewHolder {
        private SlantedTextView infaceItemMessage;
        private LinearLayout chageeListContent;
        private Button item_delete;
        private SwipeMenuLayout swipmenulayout;
        private TextView itemNumber, releasePeople, releaseData;
        private TextView chagedNoticeOrgname;
        private TextView auserName, ruserName, noticefinishcount;

        public TypeContent(View itemView) {
            super(itemView);
            infaceItemMessage = itemView.findViewById(R.id.inface_item_message);
            swipmenulayout = itemView.findViewById(R.id.swipmenu);
            chageeListContent = itemView.findViewById(R.id.chagee_list_content);
            item_delete = itemView.findViewById(R.id.item_delete);
            itemNumber = itemView.findViewById(R.id.item_number);
            releasePeople = itemView.findViewById(R.id.release_people);
            releaseData = itemView.findViewById(R.id.release_data);
            chagedNoticeOrgname = itemView.findViewById(R.id.chaged_notice_orgname);
            auserName = itemView.findViewById(R.id.auserName);
            ruserName = itemView.findViewById(R.id.ruserName);
            noticefinishcount = itemView.findViewById(R.id.noticefinishcount);
        }
    }

    class TypeExpty extends RecyclerView.ViewHolder {


        public TypeExpty(View itemView) {
            super(itemView);

        }
    }

    public void setNewData(ArrayList<ChagedList> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public interface onSwipeListener {
        void onDel(int pos, SwipeMenuLayout layout);

        void onClick(int pos);
    }

    private onSwipeListener mOnSwipeListener;


    public void setOnDelListener(onSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }


}
