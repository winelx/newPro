package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * 任务详情回复部分的适配器
 * Created by Administrator on 2017/12/13 0013.
 */

public class RecycleCommAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Aduio_comm> mDatas;

    public RecycleCommAdapterType(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.auditadapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(Viewholder holder, final int posotion) {
        holder.audi_user.setText(mDatas.get(posotion).getReplyUserName());
        holder.audi_content.setText(mDatas.get(posotion).getContent());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.image_viewpager.setLayoutManager(linearLayoutManager);
        ArrayList<String> path = new ArrayList<>();
        DialogRecAdapter adapter = new DialogRecAdapter(mContext, path,false);
        holder.image_viewpager.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView audi_user, adui_user_in, audi_content;
        private RecyclerView image_viewpager;

        public Viewholder(View itemView) {
            super(itemView);
            audi_user = (TextView) itemView.findViewById(R.id.audi_user);
            adui_user_in = (TextView) itemView.findViewById(R.id.adui_user_in);
            audi_content = (TextView) itemView.findViewById(R.id.audi_content);
            image_viewpager = itemView.findViewById(R.id.image_viewpager);
        }
    }

    public void getComm(ArrayList<Aduio_comm> mDatas) {
        this.mDatas = mDatas;
    }
}
