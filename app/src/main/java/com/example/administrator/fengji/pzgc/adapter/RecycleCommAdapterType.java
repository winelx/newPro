package com.example.administrator.fengji.pzgc.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.bean.Aduio_comm;

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
        //回复人名字
        String username = mDatas.get(posotion).getReplyUserName();
        //回复内容
        int lenght = username.length();
        String content = mDatas.get(posotion).getContent();
        content = username + "：" + content;
        if (username.length() > 0) {
            holder.audi_user.setText(setText(content, lenght, R.color.colorAccent, R.color.gray));
        } else {
            holder.audi_user.setText(content);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.image_viewpager.setLayoutManager(linearLayoutManager);
        ArrayList<String> pathMin = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        pathMin.addAll(mDatas.get(posotion).getFilePathsMin());
        path.addAll(mDatas.get(posotion).getFilePaths());
        CommentsRecAdapter adapter = new CommentsRecAdapter(mContext, pathMin, path, false);
        holder.image_viewpager.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView audi_user;
        private RecyclerView image_viewpager;

        public Viewholder(View itemView) {
            super(itemView);
            audi_user = (TextView) itemView.findViewById(R.id.audi_user);
            image_viewpager = itemView.findViewById(R.id.image_viewpager);
        }
    }

    public void getComm(ArrayList<Aduio_comm> mDatas) {
        this.mDatas = mDatas;
    }


    /**
     * 设置有颜色文字
     */
    public SpannableString setText(String str, int num, int color, int color2) {
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color)), 0,
                num - 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), num,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
