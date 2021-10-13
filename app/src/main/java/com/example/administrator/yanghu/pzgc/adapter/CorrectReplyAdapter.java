package com.example.administrator.yanghu.pzgc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.bean.CorrectReplyBean;
import com.example.administrator.yanghu.pzgc.bean.ProblemitemBean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:
 * @Activity：CorrectReplyActivity
 */

public class CorrectReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CorrectReplyBean> list;
    private Context mContext;


    public CorrectReplyAdapter(ArrayList<CorrectReplyBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CorrectReplyAdapter.Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_correctreply, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Viewholder) {
            ProblemitemBean bean = list.get(position).getBean();
            ((Viewholder) holder).title.setText("第" + (position + 1) + "个问题");
            String Rectif = bean.getRectificationOpinion();
            if (bean.getRectificationOpinion() != null) {
                Rectif = bean.getRectificationOpinion();
            } else {
                Rectif = "";
            }
            ((Viewholder) holder).correctContent.setText(
                    "隐患等级：" + bean.getHTLName() + "\n"
                            + "整改期限：" + bean.getTerm() + "\n"
                            + "整改事由：" + isnull(bean.getCause()) + "\n"
                            + "整改意见：" + Rectif + "\n"
                            + "巡检附件：");
            ((Viewholder) holder).correct_cuse.setText("违反标准：" + bean.getCisName());
            //整改描述
            ((Viewholder) holder).replyEditext.setText(bean.getReply());
            //回复附件
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((Viewholder) holder).patrolRecyclerview.setLayoutManager(linearLayoutManager);
            DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
            divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
            ((Viewholder) holder).patrolRecyclerview.addItemDecoration(divider);
            final FiletypeAdapter adapter = new FiletypeAdapter(mContext, list.get(position).getFilelist());
            ((Viewholder) holder).patrolRecyclerview.setAdapter(adapter);
            //巡检附件
            ((Viewholder) holder).replyRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
            DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
            divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
            ((Viewholder) holder).replyRecyclerview.addItemDecoration(divider1);
            CheckPhotoAdapter photoAdapter = new CheckPhotoAdapter(mContext, list.get(position).getList(), "device", true);
            ((Viewholder) holder).replyRecyclerview.setAdapter(photoAdapter);
            photoAdapter.setOnItemClickListener(new CheckPhotoAdapter.OnItemClickListener() {
                @Override
                public void addlick(View view, int adapterposition) {
                    mOnItemClickListener.addlick(position, adapterposition);
                }

                @Override
                public void deleteClick(View view, int adapterposition) {
                    mOnItemClickListener.deleteClick(position, adapterposition);
                }
            });
            ((Viewholder) holder).replyEditext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str1 = s.toString();
//                    map.put(position, str1);
                    list.get(position).getBean().setReply(str1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setNewData(ArrayList<CorrectReplyBean> lists) {
        this.list = lists;
        notifyDataSetChanged();
    }

    public void setupdate(ArrayList<CorrectReplyBean> lists, int position) {
        this.list = lists;
        notifyItemChanged(position);
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView correctContent, title, correct_cuse;
        RecyclerView replyRecyclerview, patrolRecyclerview;
        EditText replyEditext;

        public Viewholder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            correct_cuse = itemView.findViewById(R.id.correct_cuse);
            //整改描述
            replyEditext = itemView.findViewById(R.id.reply_editext);
            //回复内容
            correctContent = itemView.findViewById(R.id.correct_content);
            //回复附件
            replyRecyclerview = itemView.findViewById(R.id.reply_recyclerview);
            //巡查附件
            patrolRecyclerview = itemView.findViewById(R.id.patrol_recyclerview);
        }
    }

    /**
     * 内部接口
     */
    public interface OnItemClickListener {
        void addlick(int position, int adapterposition);

        void deleteClick(int position, int adapterposition);
    }

    private CorrectReplyAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(CorrectReplyAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private String isnull(String string) {
        if (string != null) {
            return string;
        } else {
            return "";
        }

    }


}
