package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsTop;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/28 0028.
 * @description:
 */

public class DeviceDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_RESULT = 2;
    private Context mContext;
    private ArrayList<Object> mData;

    public DeviceDetailsAdapter(Context mContext, ArrayList<Object> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TOP:
                return new DetailsTop(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_details_top, parent, false));
            case TYPE_CONTENT:
                return new DetailsReply(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_detaiils_content, parent, false));
            case TYPE_RESULT:
                return new Detailsproving(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_delaiils_contents, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = mData.get(position);
        if (holder instanceof DetailsTop) {
            bindTop(obj, position);
        } else if (holder instanceof DetailsReply) {
            bindContet(obj, position);
        } else if (holder instanceof Detailsproving) {
            bindContets(obj, position);
        }
    }

    //顶部
    private void bindTop(Object obj, int position) {
        CheckDetailsTop top = (CheckDetailsTop) obj;
    }

    //
    private void bindContet(Object obj, int position) {
    }

    private void bindContets(Object obj, int position) {
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof DetailsTop) {
            return TYPE_TOP;
        } else if (mData.get(position) instanceof DetailsReply) {
            return TYPE_CONTENT;
        } else if (mData.get(position) instanceof Detailsproving) {
            return TYPE_RESULT;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class DetailsTop extends RecyclerView.ViewHolder {
        public DetailsTop(View itemView) {
            super(itemView);
        }
    }

    class DetailsReply extends RecyclerView.ViewHolder {
        public DetailsReply(View itemView) {
            super(itemView);
        }
    }

    class Detailsproving extends RecyclerView.ViewHolder {
        public Detailsproving(View itemView) {
            super(itemView);
        }
    }
}
