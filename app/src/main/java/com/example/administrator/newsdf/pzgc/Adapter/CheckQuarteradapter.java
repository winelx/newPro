package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.CheckQuarterBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/27 0027.
 * 统计报表
 */

public class CheckQuarteradapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CheckQuarterBean> mData;
    private Context mContext;

    public CheckQuarteradapter(Context mContext, ArrayList<CheckQuarterBean> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_checkquarter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            bindView((CheckQuarteradapter.ViewHolder) holder, position);
        }
    }

    private void bindView(ViewHolder holder, int position) {
        holder.listRanking.setText((position + 1) + "");
        holder.listCompanyName.setText("所属公司：" + mData.get(position).getCompany());
        holder.listOrgidName.setText(mData.get(position).getOrgname());
        String str=mData.get(position).getNumber().replace(".0", "");
        holder.number.setText( str);
        if (position == 0) {
            holder.listRanking.setBackgroundResource(R.drawable.home_item_one);
        } else if (position == 1) {
            holder.listRanking.setBackgroundResource(R.drawable.home_item_two);
        } else if (position == 2) {
            holder.listRanking.setBackgroundResource(R.drawable.home_item_three);
        } else if (position == mData.size() - 1 || position == mData.size() - 2 || position == mData.size() - 3) {
            if (mData.size() > 3) {
                holder.listRanking.setBackgroundResource(R.drawable.check_item_last);
            }
        } else {
            holder.listRanking.setBackgroundResource(R.drawable.check_item_green);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView listOrgidName, listCompanyName, number, listRanking;

        public ViewHolder(View itemView) {
            super(itemView);
            listOrgidName = itemView.findViewById(R.id.list_orgid_name);
            listCompanyName = itemView.findViewById(R.id.list_company_name);
            number = itemView.findViewById(R.id.number);
            listRanking = itemView.findViewById(R.id.list_ranking);
        }
    }

    public void getData(ArrayList<CheckQuarterBean> Paths) {
        this.mData = Paths;
        notifyDataSetChanged();
    }
}
