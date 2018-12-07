package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.mine.Text;
import com.example.administrator.newsdf.pzgc.bean.NewDeviceBean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/30 0030.
 * @description: 新增特种设备整改适配器
 * @Activity NewDeviceActivity
 */

public class NewDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<NewDeviceBean> list;

    public NewDeviceAdapter(Context mContext, ArrayList<NewDeviceBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_newdevice, parent, false);
        return new NewDeviceAdapter.Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Viewholder) {
            ((Viewholder) holder).new_inspect_org.setText(list.get(position).getTitile());
            ((Viewholder) holder).problem.setText("第" + (position + 1) + "个问题");
            ((Viewholder) holder).new_inspect_org.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onclick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView new_inspect_org, problem;

        public Viewholder(View itemView) {
            super(itemView);
            new_inspect_org = itemView.findViewById(R.id.new_inspect_org);
            problem = itemView.findViewById(R.id.problem);
        }
    }

    public void setNewDate(ArrayList<NewDeviceBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onclick(View view, int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
