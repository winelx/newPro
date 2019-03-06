package com.example.administrator.newsdf.pzgc.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChagedreplyDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.example.administrator.newsdf.pzgc.callback.Onclicktener;
import com.example.administrator.newsdf.pzgc.utils.Enums;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> list;
    public static final int TYPE_ONE = 01;
    public static final int TYPE_TWO = 02;
    public static final int TYPE_THREE = 03;
    private Onclicktener onclicktener;

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
    private void bindNotice(NoticedViewHolder holder, final int position) {
        holder.Noticedcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.NOTICE, position);
            }
        });
    }

    /*代办事项*/
    private void bindAgency(AgencyViewHolder holder, final int position) {
        holder.Agencycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.AGENCY, position);
            }
        });
    }
    /*已办事项*/
    private void bindComplete(CompleteViewHolder holder, final int position) {

        holder.Completecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.COMPLETE, position);
            }
        });
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
        private CardView Noticedcardview;

        public NoticedViewHolder(View itemView) {
            super(itemView);
            Noticedcardview = itemView.findViewById(R.id.cardview);
        }
    }

    class AgencyViewHolder extends RecyclerView.ViewHolder {
        private CardView Agencycardview;

        public AgencyViewHolder(View itemView) {
            super(itemView);
            Agencycardview = itemView.findViewById(R.id.cardview);
        }
    }

    class CompleteViewHolder extends RecyclerView.ViewHolder {
        private CardView Completecardview;

        public CompleteViewHolder(View itemView) {
            super(itemView);
            Completecardview = itemView.findViewById(R.id.cardview);
        }
    }


    public void setOnclicktener(Onclicktener onclicktener) {
        this.onclicktener = onclicktener;
    }
}
