package com.example.zcjlmodule.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zcjlmodule.R;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/21 0021.
 * @description:
 */

public class ProcedurePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> list;
    private final static int HEAD_COUNT = 1;
    private static final int TYPE_HEARD = 1;
    private static final int TYPE_DATA = 2;
    private static final int TYPE_END = 3;
    private Context mContext;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private int lastAnimatedPosition = -1;
    private TypeItemOnClickListener mItemClickListener;

    public ProcedurePageAdapter(ArrayList<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEARD) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_type_hand_zc, parent, false);
            return new ProcedurePageAdapter.TypeHeadViewHolder(itemView);
        } else if (viewType == TYPE_DATA) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_type_data_zc, parent, false);
            return new ProcedurePageAdapter.TypeDataViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_type_end_zc, parent, false);
            return new ProcedurePageAdapter.TypeEndViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        runEnterAnimation(holder.itemView, position);
        if (holder instanceof ProcedurePageAdapter.TypeHeadViewHolder) {
        } else if (holder instanceof ProcedurePageAdapter.TypeEndViewHolder) {
        } else {
            ProcedurePageAdapter.TypeDataViewHolder holder1 = (TypeDataViewHolder) holder;
            ((TypeDataViewHolder) holder).RelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size() + 2;
        } else {
            return 0;
        }

    }

    public void setNewData(ArrayList<String> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void setNewData(ArrayList<String> data, int position) {
        int size = list.size() + 1;
        this.list.addAll(data);
        notifyItemChanged(size);
    }

    //设置展示的type
    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if (position == 0) {
            // 头部
            return TYPE_HEARD;
        } else if (position == HEAD_COUNT + contentSize) {
            // 尾部
            return TYPE_END;
        } else {
            //内容
            return TYPE_DATA;
        }
    }

    public int getContentSize() {
        return list.size();
    }


    public class TypeHeadViewHolder extends RecyclerView.ViewHolder {
        public TypeHeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TypeDataViewHolder extends RecyclerView.ViewHolder {
        //标题
        TextView title;
        //提交人或者审批人
        TextView submission;
        //时间
        TextView data;
        //意见
        TextView opinion;
        RelativeLayout RelativeLayout;

        public TypeDataViewHolder(View itemView) {
            super(itemView);
            opinion = itemView.findViewById(R.id.type_opinion);
            data = itemView.findViewById(R.id.data_time);
            title = itemView.findViewById(R.id.title);
            submission = itemView.findViewById(R.id.type_name);
            RelativeLayout = itemView.findViewById(R.id.type_item);
        }
    }

    public class TypeEndViewHolder extends RecyclerView.ViewHolder {
        public TypeEndViewHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * item显示动画
     *
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;              //animationsLocked是布尔类型变量，一开始为false
        //确保仅屏幕一开始能够容纳显示的item项才开启动画
        if (position > lastAnimatedPosition) {//lastAnimatedPosition是int类型变量，默认-1，
            //这两行代码确保了recyclerview滚动式回收利用视图时不会出现不连续效果
            lastAnimatedPosition = position;
            view.setTranslationY(500);     //Item项一开始相对于原始位置下方500距离
            view.setAlpha(0.f);           //item项一开始完全透明
            //每个item项两个动画，从透明到不透明，从下方移动到原始位置
            view.animate()
                    .translationY(0).alpha(1.f)                                //设置最终效果为完全不透明
                    //并且在原来的位置
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)//根据item的位置设置延迟时间
                    //达到依次动画一个接一个进行的效果
                    .setInterpolator(new DecelerateInterpolator(0.5f))     //设置动画位移先快后慢的效果
                    .setDuration(700)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                            //确保仅屏幕一开始能够显示的item项才开启动画
                            //也就是说屏幕下方还没有显示的item项滑动时是没有动画效果
                        }
                    })
                    .start();
        }
    }

    public interface TypeItemOnClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(TypeItemOnClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}

