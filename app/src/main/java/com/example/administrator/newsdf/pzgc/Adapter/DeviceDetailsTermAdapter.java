package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.device.SeeDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.DeviceTrem;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class DeviceDetailsTermAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<DeviceTrem> list;
    private Context mContext;

    public DeviceDetailsTermAdapter(ArrayList<DeviceTrem> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceDetailsTermAdapter.viewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.device_term, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            ((viewHolder) holder).term_content.setText(list.get(position).getCisName());
            boolean status = list.get(position).isReplied();
            if (status) {
                ((viewHolder) holder).circular_ensure.setBackgroundResource(R.mipmap.circular_ensure_true);
            } else {
                ((viewHolder) holder).circular_ensure.setBackgroundResource(R.mipmap.circular_ensure_false);
            }
            ((viewHolder) holder).term_content_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickitemlitener.seedetails();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView term_content;
        ImageView circular_ensure;
        LinearLayout term_content_linear;

        public viewHolder(View itemView) {
            super(itemView);
            term_content = itemView.findViewById(R.id.term_content);
            circular_ensure = itemView.findViewById(R.id.circular_ensure);
            term_content_linear = itemView.findViewById(R.id.term_content_linear);
        }
    }
    public interface onclickitemlitener {
        void seedetails();
    }

    private DeviceDetailsAdapter.onclickitemlitener onclickitemlitener;

    public void setOnclickItemLitener(DeviceDetailsAdapter.onclickitemlitener litener) {
        this.onclickitemlitener = litener;
    }
}
