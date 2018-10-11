package com.example.zcjlmodule.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zcjlmodule.bean.WorkBean;
import com.example.zcmodule.R;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:
 */

public class WorkFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WorkBean> mData;
    private Context mContext;
    private WorkFragmentItemAdapter TypeAdapter;

    public WorkFragmentAdapter(Context mContext, List<WorkBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_work_fragment_zc, parent, false);
        TypeViewholder vh = new TypeViewholder(view);
        //将创建的View注册点击事件
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeViewholder && mData.size() > 0) {
            bindGrid((TypeViewholder) holder, position);
        }
    }

    private void bindGrid(TypeViewholder holder, int position) {
        holder.workFrTypeTitle.setText(mData.get(position).getTitle());
        holder.workFrTypeRecycler.setLayoutManager(new GridLayoutManager(holder.workFrTypeRecycler.getContext(), 4));
        TypeAdapter = new WorkFragmentItemAdapter(mContext, mData.get(position).getList());
        holder.workFrTypeRecycler.setAdapter(TypeAdapter);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TypeViewholder extends RecyclerView.ViewHolder {
        TextView workFrTypeTitle;
        RecyclerView workFrTypeRecycler;

        public TypeViewholder(View itemView) {
            super(itemView);
            workFrTypeTitle = itemView.findViewById(R.id.work_fr_type_title);
            workFrTypeRecycler = itemView.findViewById(R.id.work_fr_type_recycler);
        }
    }
}
