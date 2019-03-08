package com.example.baselibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class EmptyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context mContext;
    private final int EMPTY_VIEW = 0;
    private final int NOT_EMPTY_VIEW = 1;
    public List<T> mDatas = new ArrayList<>();

    public EmptyAdapter(Context context) {
        this.mContext = context;
    }

    public EmptyAdapter(List<T> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mContext = context;
    }

    public void refreshData(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //获取传入adapter的条目数，没有则返回 1
        //位空视图保留一个条目
        return (mDatas != null && mDatas.size() > 0) ? mDatas.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        //根据传入adapter来判断是否有数据
        if (mDatas != null && mDatas.size() > 0) {
            return NOT_EMPTY_VIEW;
        }
        return EMPTY_VIEW;
    }

    public abstract RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType);

    public abstract void bindMyViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //展示空视图或者调用传入adapter方法
        if (viewType == EMPTY_VIEW) {
            return new EmptyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.base_layout_emptyview, parent, false));
        }
        return createMyViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == EMPTY_VIEW) {
            return;
        }
        bindMyViewHolder(holder, position);
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
