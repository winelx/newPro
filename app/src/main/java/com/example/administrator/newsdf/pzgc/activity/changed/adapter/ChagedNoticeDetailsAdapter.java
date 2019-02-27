package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：通知单详情
 * {@link  ChagedNoticeDetailsActivity}
 */
public class ChagedNoticeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEARD = 1;
    private static final int TYPE_DATA = 2;
    private Context mContext;
    private ArrayList<Object> list;

    public ChagedNoticeDetailsAdapter(Context mContext, ArrayList<Object> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEARD:
                return new TypeContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_noticedetails, parent, false));
            case TYPE_DATA:
                return new TypeCheckItem(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_noticedetailslist, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (holder instanceof TypeContent) {
            bindTop((TypeContent) holder, obj, position);
        } else if (holder instanceof TypeCheckItem) {
            bindContet((TypeCheckItem) holder, list, position);
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindTop(TypeContent holder, Object obj, int position) {
        ChagedNoticeDetails item = (ChagedNoticeDetails) list.get(position);
        if (list.size() == 1) {
            holder.problemItem.setVisibility(View.GONE);
        } else {
            holder.problemItem.setVisibility(View.VISIBLE);
        }
        /*编号*/
        holder.noticedNumber.setText("编号："+item.getCode());
        /*下发人*/
        holder.noticedSendPeople.setText("下发人：" + item.getSendUserName());
        if (item.getSend_date() != null) {
            holder.noticedSendData.setText("下发日期：" + item.getSend_date().substring(0, 10));
        } else {
            holder.noticedSendData.setText("下发日期：");
        }

        holder.noticedChagedOrg.setText("整改组织：" + item.getRorgName());
        holder.noticedChagedPopple.setText("整改负责人：" + item.getRuserName());
        holder.noticedSendOrg.setText("下发组织：" + item.getSorgName());
        holder.noticedAusername.setText("待处理人：" + item.getAuserName());
        /*通知单完成数*/
        int noticeFinishCount = item.getNoticeFinishCount();
        String leanht = noticeFinishCount + "";
        /*总下发通知单数*/
        int noticeCount = item.getNoticeCount();
        holder.noticeCompleteProportion.setText(Dates.setText(mContext, "完成比例：" + noticeFinishCount + "/" + noticeCount, 5, 5 + leanht.length(), R.color.finish_green));

    }

    private void bindContet(TypeCheckItem holder, ArrayList<Object> list, final int position) {
        Object obj = list.get(position);
        final ChagedNoticeDetailslsit item = (ChagedNoticeDetailslsit) obj;
        holder.noticeListContent.setText(item.getStandardDelName());
        String string = item.getIsOverdue()+"";
        //是否超时
        if ("1".equals(string)) {
            holder.overtime.setVisibility(View.GONE);
        } else if ("2".equals(string)) {
            holder.overtime.setBackgroundResource(R.mipmap.overtime);
        } else if ("3".equals(string)) {
            holder.overtime.setBackgroundResource(R.mipmap.noovertime);
        }
        //是否通过
        final String isVerify = item.getIsVerify()+"";
        if ("1".equals(isVerify)) {
            //未完成
            holder.complete.setBackgroundResource(R.mipmap.chagednocomplete);
        } else {
            holder.complete.setBackgroundResource(R.mipmap.chagedcomplete);
        }
        holder.itemProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position, item.getId());
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof ChagedNoticeDetails) {
            return TYPE_HEARD;
        } else if (list.get(position) instanceof ChagedNoticeDetailslsit) {
            return TYPE_DATA;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    /*单据详情*/
    class TypeContent extends RecyclerView.ViewHolder {
        private TextView problemItem;
        private TextView noticedNumber;
        private TextView noticedSendPeople, noticedChagedOrg, noticedSendData,
                noticedChagedPopple, noticedSendOrg, noticedAusername, noticeCompleteProportion;

        public TypeContent(View itemView) {
            super(itemView);
            problemItem = itemView.findViewById(R.id.problem_item);
            //编号
            noticedNumber = itemView.findViewById(R.id.noticed_number);
            noticedSendPeople = itemView.findViewById(R.id.noticed_send_people);
            noticedChagedOrg = itemView.findViewById(R.id.noticed_chaged_org);
            noticedChagedPopple = itemView.findViewById(R.id.noticed_chaged_popple);
            noticedSendOrg = itemView.findViewById(R.id.noticed_send_org);
            noticedAusername = itemView.findViewById(R.id.noticed_auserName);
            noticeCompleteProportion = itemView.findViewById(R.id.notice_complete_proportion);
            noticedSendData = itemView.findViewById(R.id.noticed_send_data);
        }
    }

    /*单据整改项*/
    class TypeCheckItem extends RecyclerView.ViewHolder {
        private TextView noticeListContent;
        private ImageView overtime, complete;
        private LinearLayout itemProblem;

        public TypeCheckItem(View itemView) {
            super(itemView);
            noticeListContent = itemView.findViewById(R.id.notice_list_content);
            itemProblem = itemView.findViewById(R.id.item_problem);
            overtime = itemView.findViewById(R.id.notice_list_status);
            complete = itemView.findViewById(R.id.complete);

        }
    }

    public void setNewData(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClick(int position, String string);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
