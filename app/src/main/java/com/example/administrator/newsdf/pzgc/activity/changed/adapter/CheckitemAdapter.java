package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.annotation.SuppressLint;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.CheckitemActivity;
import com.example.administrator.newsdf.pzgc.bean.Checkitem;
import com.example.administrator.newsdf.pzgc.inter.ItemClickListener;


import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：MainActivity
 * {@link CheckitemActivity}
 */
public class CheckitemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> list;
    private static final int TYPE_HEARD = 1;
    private static final int TYPE_DATA = 2;

    public CheckitemAdapter(ArrayList<Object> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEARD:
                return new TypeContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_chaged_checkitem, parent, false));
            case TYPE_DATA:
                return new TypeTitle(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_chaged_checkitemtitle, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeContent) {
            bindContent((TypeContent) holder, list, position);
        } else if (holder instanceof TypeTitle) {
            bindTitle((TypeTitle) holder, list, position);
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindContent(TypeContent holder, ArrayList<Object> list, final int position) {
        final Checkitem checkitem = (Checkitem) list.get(position);
        if (checkitem.isLeam()) {
            holder.checkimage.setBackgroundResource(R.mipmap.circular_ensure_true);
        } else {
            holder.checkimage.setBackgroundResource(R.mipmap.circular_ensure_false);
        }
        holder.checkItem.setText(
                "检测内容：" + "测试检测内容测试检测内容测试检测" + "\n"
                        + "检测标准：" + "检测标准检测标准检测标准检测标准检测标准检测标准检测标准" + "\n"
                        + "具体描述：" + "具体描述具体描述具体描述具"
        );
        holder.check_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkitem.isLeam()) {
                    //如果已经选择，点击取消 //如果已经选择，再次点击取消
                    listener.ondelete("第" + position + "个", position);
                } else {
                    //如果没有选择，点击标记
                    listener.Onclick("第" + position + "个", position);
                }
            }
        });
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void bindTitle(TypeTitle holder, ArrayList<Object> list, int position) {
        holder.title.setText("测试标题" + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Checkitem) {
            return TYPE_HEARD;
        } else if (list.get(position) instanceof String) {
            return TYPE_DATA;
        } else {
            return super.getItemViewType(position);
        }
    }

    class TypeContent extends RecyclerView.ViewHolder {
        private TextView checkItem;
        private ImageView checkimage;
        private LinearLayout check_lin;

        public TypeContent(View itemView) {
            super(itemView);
            checkItem = itemView.findViewById(R.id.check_item);
            checkimage = itemView.findViewById(R.id.check_image);
            check_lin = itemView.findViewById(R.id.check_lin);
        }
    }

    class TypeTitle extends RecyclerView.ViewHolder {
        TextView title;

        public TypeTitle(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.checkitemtitle);
        }
    }

    public interface ItemClickListener {
        void Onclick(String str, int position);

        void ondelete(String str, int position);
    }

    public ItemClickListener listener;

    public void setItemOnclick(ItemClickListener listeners) {
        this.listener = listeners;
    }

    public void setNewData(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
