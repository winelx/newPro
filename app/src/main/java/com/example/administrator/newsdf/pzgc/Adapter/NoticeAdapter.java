package com.example.administrator.newsdf.pzgc.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
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
        NoticedBean bean = (NoticedBean) list.get(position);
        holder.noticedcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.NOTICE, position);
            }
        });
        //时间
        if (bean.getNoticeDate() != null) {
            holder.noticedData.setText(bean.getNoticeDate());
        }
        //标题
        if (bean.getModelName() != null) {
            holder.noticedTitle.setText(bean.getModelName());
        }
        //内容
        if (bean.getShowContent() != null) {
            holder.noticedContent.setText(bean.getShowContent());
        }

    }

    /*代办事项*/
    private void bindAgency(AgencyViewHolder holder, final int position) {
        holder.Agencycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.AGENCY, position);
            }
        });
        AgencyBean bean = (AgencyBean) list.get(position);
        holder.agencyContent.setText("你有一条" + bean.getModelName() + "(" + bean.getModelCode() + ")" + "需要处理。");
        holder.agencyData.setText(bean.getSendDate());
        holder.agencyTitle.setText(bean.getModelName());
    }

    /*已办事项*/
    @SuppressLint("SetTextI18n")
    private void bindComplete(CompleteViewHolder holder, final int position) {
        holder.Completecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktener.onClick(Enums.COMPLETE, position);
            }
        });
        CompleteBean bean = (CompleteBean) list.get(position);
        holder.modelname.setText(bean.getModelName()  + "(" + bean.getModelCode() + ")" + "已处理");
        holder.title.setText(bean.getModelName());
        holder.complete_result.setText("处理结果：" + bean.getDealResult());
        holder.complete_data.setText("处理时间：" + bean.getSendDate());
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
        private CardView noticedcardview;
        private TextView noticedData, noticedTitle, noticedContent;

        public NoticedViewHolder(View itemView) {
            super(itemView);
            noticedcardview = itemView.findViewById(R.id.cardview);
            noticedData = itemView.findViewById(R.id.noticed_data);
            noticedTitle = itemView.findViewById(R.id.noticed_title);
            noticedContent = itemView.findViewById(R.id.noticed_content);
        }
    }

    /*待办事项*/
    class AgencyViewHolder extends RecyclerView.ViewHolder {
        private CardView Agencycardview;
        private TextView agencyData, agencyTitle, agencyContent;

        public AgencyViewHolder(View itemView) {
            super(itemView);
            Agencycardview = itemView.findViewById(R.id.cardview);
            agencyData = itemView.findViewById(R.id.agency_data);
            agencyTitle = itemView.findViewById(R.id.agency_title);
            agencyContent = itemView.findViewById(R.id.agency_content);
        }
    }

    /*已办事项*/
    class CompleteViewHolder extends RecyclerView.ViewHolder {
        private CardView Completecardview;
        private TextView modelname, title, complete_result, complete_data;

        public CompleteViewHolder(View itemView) {
            super(itemView);
            Completecardview = itemView.findViewById(R.id.cardview);
            modelname = itemView.findViewById(R.id.complete_modelname);
            title = itemView.findViewById(R.id.complete_title);
            complete_result = itemView.findViewById(R.id.complete_result);
            complete_data = itemView.findViewById(R.id.complete_data);
        }
    }

    public void setOnclicktener(Onclicktener onclicktener) {
        this.onclicktener = onclicktener;
    }

    public void setNewData(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
