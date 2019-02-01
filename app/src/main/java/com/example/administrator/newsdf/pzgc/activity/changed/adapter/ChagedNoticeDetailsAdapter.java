package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;
import com.example.administrator.newsdf.pzgc.bean.DeviceDetailsTop;
import com.example.administrator.newsdf.pzgc.bean.DeviceReflex;

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
        View itemView = null;
        switch (viewType) {
            case TYPE_HEARD:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_noticedetails, parent, false);
                break;
            case TYPE_DATA:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_noticedetailslist, parent, false);
                break;
            default:
                break;
        }
        return new CheckPhotoAdapter.PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (holder instanceof TypeContent) {
            bindTop((TypeContent) holder, obj, position);
        } else if (holder instanceof TypeCheckItem) {
            bindContet((TypeCheckItem) holder, obj, position);
        }
    }


    private void bindTop(TypeContent holder, Object obj, int position) {
        if (list.size() == 1) {
            holder.problem_item.setVisibility(View.GONE);
        } else {
            holder.problem_item.setVisibility(View.VISIBLE);
        }

    }

    private void bindContet(TypeCheckItem holder, Object obj, int position) {
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
        return list.size();
    }

    class TypeContent extends RecyclerView.ViewHolder {
        private TextView problem_item;

        public TypeContent(View itemView) {
            super(itemView);
            problem_item = itemView.findViewById(R.id.problem_item);
        }
    }

    class TypeCheckItem extends RecyclerView.ViewHolder {
        public TypeCheckItem(View itemView) {
            super(itemView);
        }
    }
}
