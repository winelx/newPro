package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:
 * @Activity：
 */

public class CorrectReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> list;
    private Context mContext;

    public CorrectReplyAdapter(ArrayList<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CorrectReplyAdapter.Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_correctreply, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Viewholder) {
            ((Viewholder) holder).correct_content.setText("违反标准：测试违反标准" + "\\n"
                    + "隐患等级：" + "B" + "\\n"
                    + "整改期限：" + "2018-12-12" + "\\n"
                    + "整改事由：" + "设备存放有问题" + "\\n"
                    + "巡检附件：");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView correct_content;

        public Viewholder(View itemView) {
            super(itemView);
            correct_content = itemView.findViewById(R.id.correct_content);
        }
    }
}
