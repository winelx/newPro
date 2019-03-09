package com.example.baselibrary.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baselibrary.R;
import com.example.baselibrary.bean.ItemBean;

import java.util.List;

/**
 * @author lx
 * @Created by: 2019/1/10 0010.
 * @description: 列表界面需要权限控制功能recycler的适配器
 * @Activity：
 */
public class MessageFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemBean> mData;
    private Context mContext;
    private MessageFragmentItemAdapter TypeAdapter;

    public MessageFragmentAdapter(Context mContext, List<ItemBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    //初始化布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_recycler_adapter, parent, false);
        TypeViewholder vh = new TypeViewholder(view);
        //将创建的View注册点击事件
        return vh;
    }

    //绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeViewholder && mData.size() > 0) {
            bindGrid((TypeViewholder) holder, position);
        }
    }

    //数据处理
    private void bindGrid(TypeViewholder holder, int position) {
        //功能
        holder.workFrTypeTitle.setText(mData.get(position).getString());
        //嵌套的recycler2view
        holder.workFrTypeRecycler.setLayoutManager(new GridLayoutManager(holder.workFrTypeRecycler.getContext(), 4));
        TypeAdapter = new MessageFragmentItemAdapter(mContext, mData.get(position).getList());
        holder.workFrTypeRecycler.setAdapter(TypeAdapter);
        TypeAdapter.setOnclickitemlitener(new MessageFragmentItemAdapter.onclickitemlitener() {
            @Override
            public void onclick(String str, int position) {
                onclickitemlitener.onclick(str, position);
            }
        });
    }

    //数据长度
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //初始化控件
    class TypeViewholder extends RecyclerView.ViewHolder {
        TextView workFrTypeTitle;
        RecyclerView workFrTypeRecycler;

        public TypeViewholder(View itemView) {
            super(itemView);
            workFrTypeTitle = itemView.findViewById(R.id.base_fr_type_title);
            workFrTypeRecycler = itemView.findViewById(R.id.base_fr_type_recycler);
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

    public MessageFragmentItemAdapter.onclickitemlitener onclickitemlitener;

    public void setOnclickitemlitener(MessageFragmentItemAdapter.onclickitemlitener onclickitemlitener) {
        this.onclickitemlitener = onclickitemlitener;
    }

    public void setNewData(List<ItemBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}
