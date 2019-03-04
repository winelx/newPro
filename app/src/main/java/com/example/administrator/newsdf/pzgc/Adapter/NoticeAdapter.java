package com.example.administrator.newsdf.pzgc.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;

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
                return new NoticedBean(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_notice, parent, false));
            case TYPE_TWO:
                return new AgencyBean(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_agency, parent, false));
            case TYPE_THREE:
                return new CompleteBean(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_complete, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoticedBean && list.size() != 0) {
            bindNotice((NoticedBean) holder, position);
        } else if (holder instanceof AgencyBean && list.size() != 0) {
            bindAgency((AgencyBean) holder, position);
        } else if (holder instanceof CompleteBean && list.size() != 0) {
            bindComplete((CompleteBean) holder, position);
        }
    }


    /*消息通知*/
    private void bindNotice(NoticedBean holder, int position) {
    }

    /*代办事项*/
    private void bindAgency(AgencyBean holder, int position) {
    }

    /*已办事项*/
    private void bindComplete(CompleteBean holder, int position) {
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
    class NoticedBean extends RecyclerView.ViewHolder {

        public NoticedBean(View itemView) {
            super(itemView);
        }
    }

    class AgencyBean extends RecyclerView.ViewHolder {

        public AgencyBean(View itemView) {
            super(itemView);
        }
    }

    class CompleteBean extends RecyclerView.ViewHolder {

        public CompleteBean(View itemView) {
            super(itemView);
        }
    }
}
