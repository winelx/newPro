package com.winelx.z.expression.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class recyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.react, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView divTitle, divImage;

        public MyHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.content);
            divTitle = itemView.findViewById(R.id.div_tyitle);
            divImage = itemView.findViewById(R.id.div_image);
        }
    }
}
