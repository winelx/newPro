package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckitemActivity;
import com.example.administrator.newsdf.pzgc.bean.ChekItemBean;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by Administrator on 2018/8/31 0031.
 * 检查项的检查标准适配器
 */

public class CheckitemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 10001;
    private static final int TYPE_TWO = 10002;

    private ArrayList<ChekItemBean> mData;
    private Context mContext;

    public CheckitemAdapter(Context mContext, ArrayList<ChekItemBean> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item_adapter, parent, false));
            case TYPE_TWO:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item_adapter2, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CheckitemAdapter.ViewHolder) {
            bindView((ViewHolder) holder, position);
        } else if (holder instanceof CheckitemAdapter.ViewHolder2) {
            bindView2((ViewHolder) holder, position);
        }
    }

    private void bindView2(ViewHolder holder, int position) {

    }

    private void bindView(final ViewHolder holder, final int position) {
        BigDecimal str = mData.get(position).getScore();
        if (str.compareTo(new BigDecimal("0")) == 0) {
            holder.textView.setText(mData.get(position).getContent());
        } else {
            holder.textView.setText(mData.get(position).getContent() + "（" + str + "分" + ")");
        }
        //判断初始状态
        if ("true".equals(mData.get(position).getStatus())) {
            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_t);
            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
        } else if ("false".equals(mData.get(position).getStatus())) {
            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_t);
        } else {
            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
        }
        holder.checkItemTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckitemActivity activity = (CheckitemActivity) mContext;
                if ("保存".equals(activity.getstatus())) {
                    if (activity.getswitchstatus()) {
                        ToastUtils.showShortToast("已选择无此项，无法进行操作");
                    } else {
                        if ("true".equals(mData.get(position).getStatus())) {
                            activity.setScore(position);
                            mData.get(position).setStatus("");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
                        } else {
                            mData.get(position).setStatus("true");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_t);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
                        }
                        activity.setScore(position);
                    }
                } else if ("编辑".equals(activity.getstatus())) {
                    ToastUtils.showShortToast("不是编辑状态哦");
                } else {
                }
            }
        });
        holder.checkItemFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckitemActivity activity = (CheckitemActivity) mContext;
                if ("保存".equals(activity.getstatus())) {
                    if (activity.getswitchstatus()) {
                        ToastUtils.showShortToast("已选择无此项，无法进行操作");
                    } else {
                        if ("false".equals(mData.get(position).getStatus())) {
                            mData.get(position).setStatus("");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_f);
                        } else {
                            mData.get(position).setStatus("false");
                            holder.checkItemTrue.setBackgroundResource(R.mipmap.checkitemsuccess_f);
                            holder.checkItemFalse.setBackgroundResource(R.mipmap.checkitemerror_t);
                        }
                        activity.setScore(position);
                    }
                } else if ("编辑".equals(activity.getstatus())) {
                    ToastUtils.showShortToast("不是编辑状态哦");
                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView checkItemTrue, checkItemFalse;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.check_item_flont);
            checkItemTrue = itemView.findViewById(R.id.check_item_true);
            checkItemFalse = itemView.findViewById(R.id.check_item_false);
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView checkItemTrue, checkItemFalse;

        public ViewHolder2(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.check_item_flont);
            checkItemTrue = itemView.findViewById(R.id.check_item_true);
            checkItemFalse = itemView.findViewById(R.id.check_item_false);
        }
    }
    public void getData(ArrayList<ChekItemBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}
