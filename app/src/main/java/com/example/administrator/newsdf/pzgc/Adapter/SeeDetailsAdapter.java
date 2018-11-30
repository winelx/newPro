package com.example.administrator.newsdf.pzgc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.mine.Text;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.bean.SeeDetailsReply;
import com.example.administrator.newsdf.pzgc.bean.SeeDetailsTop;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class SeeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private ArrayList<Object> mData;
    private Context mContext;
    private int page = 0;

    public SeeDetailsAdapter(Context mContext, ArrayList<Object> list) {
        this.mData = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new SeeDetailsAdapter.TopViewholder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.see_details_top, parent, false));
            case TYPE_TWO:
                return new SeeDetailsAdapter.ReplyViewholder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.see_details_reply, parent, false));
            default:
                return null;
        }
    }

    /**
     * @param holder
     * @param position
     * @绑定viewholder
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = mData.get(position);
        if (holder instanceof TopViewholder) {

            problem((TopViewholder) holder, obj, position);
        } else if (holder instanceof ReplyViewholder) {
            reply((ReplyViewholder) holder, obj, position);
        }
    }

    @SuppressLint("SetTextI18n")
    private void problem(TopViewholder holder, Object object, int position) {
        page++;
        ArrayList<FileTypeBean> list = new ArrayList<>();
        list.addAll(((SeeDetailsTop) object).getList());
        holder.seeDetailsTitle.setText("第" + page + "个问题");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.toprecycler.setLayoutManager(linearLayoutManager);
        FiletypeAdapter adapter2 = new FiletypeAdapter(mContext, list);
        holder.toprecycler.setAdapter(adapter2);

    }

    private void reply(ReplyViewholder holder, Object object, int position) {
        ArrayList<FileTypeBean> list = new ArrayList<>();
        list.addAll(((SeeDetailsReply) object).getList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.seedetailsreplyrecycler.setLayoutManager(linearLayoutManager);
        FiletypeAdapter adapter2 = new FiletypeAdapter(mContext, list);
        holder.seedetailsreplyrecycler.setAdapter(adapter2);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof SeeDetailsTop) {
            return TYPE_ONE;
        } else if (mData.get(position) instanceof SeeDetailsReply) {
            return TYPE_TWO;
        } else {
            return super.getItemViewType(position);
        }
    }

    class TopViewholder extends RecyclerView.ViewHolder {
        RecyclerView toprecycler;
        TextView seeDetailsTitle;
        TextView seeDetailsStandard;
        TextView seeDetailsGrade;
        TextView seeDetailsTerm;
        TextView seeDetailsReason;

        public TopViewholder(View itemView) {
            super(itemView);
            toprecycler = itemView.findViewById(R.id.see_details_toprecycler);
            seeDetailsTitle = itemView.findViewById(R.id.see_details_title);
            seeDetailsStandard = itemView.findViewById(R.id.see_details_standard);
            seeDetailsGrade = itemView.findViewById(R.id.see_details_grade);
            seeDetailsTerm = itemView.findViewById(R.id.see_details_term);
            seeDetailsReason = itemView.findViewById(R.id.see_details_reason);
        }
    }

    class ReplyViewholder extends RecyclerView.ViewHolder {
        RecyclerView seedetailsreplyrecycler;
        TextView seedetailsdescribe;

        public ReplyViewholder(View itemView) {
            super(itemView);
            seedetailsreplyrecycler = itemView.findViewById(R.id.see_details_replyrecycler);
            seedetailsdescribe = itemView.findViewById(R.id.see_details_describe);

        }
    }
}
