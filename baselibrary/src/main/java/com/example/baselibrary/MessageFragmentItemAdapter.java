package com.example.baselibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.bean.bean;

import java.util.List;



/**
 * @author lx
 * @Created by: 2019/1/10 0010.
 * @description: 列表界面需要权限控制功能recycler的适配器嵌套的recycler的适配器
 * @Activity：
 */
public class MessageFragmentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<bean> list;
    private Context mContext;

    public MessageFragmentItemAdapter(Context mContext, List<bean> list) {
        this.list = list;
        this.mContext = mContext;
    }

    public MessageFragmentItemAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_recycler_item_adapter, parent, false);
        TypeHolder holder = new TypeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeHolder && list.size() > 0) {
            bindView((TypeHolder) holder, position);
        }
    }

    private void bindView(TypeHolder holder, final int position) {
        holder.iamge.setBackgroundResource(list.get(position).getIcon());
        holder.content.setText(list.get(position).getName());
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = list.get(position).getName();
                onclickitemlitener.onclick(content, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TypeHolder extends RecyclerView.ViewHolder {
        ImageView iamge;
        TextView content;
        LinearLayout line;

        public TypeHolder(View itemView) {
            super(itemView);
            iamge = itemView.findViewById(R.id.work_rec_item_iamge);
            content = itemView.findViewById(R.id.work_rec_item_content);
            line = itemView.findViewById(R.id.work_rec_item_line);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface onclickitemlitener {
        void onclick(String str, int position);
    }

    public onclickitemlitener onclickitemlitener;

    public void setOnclickitemlitener(onclickitemlitener onclickitemlitener) {
        this.onclickitemlitener = onclickitemlitener;
    }
}
