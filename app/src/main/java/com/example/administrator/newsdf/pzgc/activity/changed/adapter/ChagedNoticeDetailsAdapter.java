package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;

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
    final static int MAX = 100;

    public ChagedNoticeDetailsAdapter(Context mContext, ArrayList<Object> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEARD:
                return new ChagedNoticeDetailsAdapter.TypeContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_noticedetails, parent, false));
            case TYPE_DATA:
                return new ChagedNoticeDetailsAdapter.TypeCheckItem(LayoutInflater.from(parent.getContext()).
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

    private void bindTop(TypeContent holder, Object obj, int position) {
        if (list.size() == 1) {
            holder.problemItem.setVisibility(View.GONE);
        } else {
            holder.problemItem.setVisibility(View.VISIBLE);
        }

    }

    private void bindContet(TypeCheckItem holder, ArrayList<Object> list, final int position) {
        Object obj = list.get(position);
        ChagedNoticeDetailslsit top = (ChagedNoticeDetailslsit) obj;
        holder.noticeListContent.setText(top.getStr().toString());
        holder.itemProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.setproblem(position);
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
        return list == null ? 0 : list.size();
    }

    class TypeContent extends RecyclerView.ViewHolder {
        private TextView problemItem;

        TypeContent(View itemView) {
            super(itemView);
            problemItem = itemView.findViewById(R.id.problem_item);
        }
    }

    class TypeCheckItem extends RecyclerView.ViewHolder {
        private LinearLayout itemProblem;
        private TextView noticeListContent;

        TypeCheckItem(View itemView) {
            super(itemView);
            itemProblem = itemView.findViewById(R.id.item_problem);
            noticeListContent = itemView.findViewById(R.id.notice_list_content);
        }
    }

    public interface OnClickListener {
        void setproblem(int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
