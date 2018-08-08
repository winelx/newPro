package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;

import java.util.ArrayList;


public class IssuedTaskDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mData;
    private Context mContext;
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;

    public IssuedTaskDetailsAdapter(ArrayList<Object> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TOP:
                return new DetailsTop(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_details_top, parent, false));
            case TYPE_CONTENT:
                return new DetailsContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_detaiils_content, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // 头部
            return TYPE_TOP;
        } else {
            //内容
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class DetailsTop extends RecyclerView.ViewHolder {

        public DetailsTop(View itemView) {
            super(itemView);
        }
    }

    class DetailsContent extends RecyclerView.ViewHolder {

        public DetailsContent(View itemView) {
            super(itemView);
        }
    }
}