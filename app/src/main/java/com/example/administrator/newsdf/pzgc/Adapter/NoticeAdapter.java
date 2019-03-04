package com.example.administrator.newsdf.pzgc.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> list;
    public static final int TYPE_ONE = 01;
    public static final int TYPE_TWO = 02;
    public static final int TYPE_THREE = 03;

    public NoticeAdapter(ArrayList<Object> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new NoticedViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_notice, parent, false));
            case TYPE_TWO:
                return new AgencyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_agency, parent, false));
            case TYPE_THREE:
                return new CompleteViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_complete, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoticedViewHolder && list.size() != 0) {
            bindNotice((NoticedViewHolder) holder, position);
        } else if (holder instanceof AgencyViewHolder && list.size() != 0) {
            bindAgency((AgencyViewHolder) holder, position);
        } else if (holder instanceof CompleteViewHolder && list.size() != 0) {
            bindComplete((CompleteViewHolder) holder, position);
        }
    }


    /*消息通知*/
    private void bindNotice(NoticedViewHolder holder, int position) {
    }

    /*代办事项*/
    private void bindAgency(AgencyViewHolder holder, int position) {
    }

    /*已办事项*/
    private void bindComplete(CompleteViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof NoticedBean) {
            return TYPE_ONE;
        } else if (list.get(position) instanceof AgencyBean) {
            return TYPE_TWO;
        } else if (list.get(position) instanceof CompleteBean) {
            return TYPE_THREE;
        } else {
            return super.getItemViewType(position);
        }
    }

    /*消息通知*/
    class NoticedViewHolder extends RecyclerView.ViewHolder {

        public NoticedViewHolder(View itemView) {
            super(itemView);
        }
    }

    class AgencyViewHolder extends RecyclerView.ViewHolder {

        public AgencyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class CompleteViewHolder extends RecyclerView.ViewHolder {

        public CompleteViewHolder(View itemView) {
            super(itemView);
        }
    }
}
