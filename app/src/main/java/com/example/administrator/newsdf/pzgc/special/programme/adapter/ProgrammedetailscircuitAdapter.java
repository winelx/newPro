package com.example.administrator.newsdf.pzgc.special.programme.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.administrator.newsdf.pzgc.utils.Utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProgrammedetailscircuitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Map<String, ArrayList<ProDetails.RecordListBean>> list;
    private ArrayList<String> titlelist;
    private final static int HEAD_COUNT = 1;
    private static final int TYPE_HEARD = 2;
    private static final int TYPE_END = 3;
    private static final int EMPTYVIEW = 4;
    private Context mContext;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private int lastAnimatedPosition = -1;
    private TypeItemOnClickListener mItemClickListener;
    private ScircuititemAdaptyer adaptyer;

    public ProgrammedetailscircuitAdapter(Map<String, ArrayList<ProDetails.RecordListBean>> lsit, ArrayList<String> titlelist, Context mContext) {
        this.list = lsit;
        this.titlelist = titlelist;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEARD) {
            //头部
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_programme_scircuit_head, parent, false);
            return new TypeHeadViewHolder(itemView);
        } else if (viewType == TYPE_END) {
            //尾部
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_programme_scircuit_footer, parent, false);
            return new TypeEndViewHolder(itemView);
        } else if (viewType == EMPTYVIEW) {
            //空白
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_programme_scircuit_emptyview, parent, false);
            return new TypeEmptyViewHolder(itemView);
        } else {
            //内容
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_programme_scircuit, parent, false);
            return new TypeDataViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        if (holder instanceof TypeHeadViewHolder) {
        } else if (holder instanceof TypeEndViewHolder) {
        } else if (holder instanceof TypeEmptyViewHolder) {

        } else {
            //内容
            content((TypeDataViewHolder) holder, position);
        }
    }

    private void content(TypeDataViewHolder holder, int position) {
        String title = titlelist.get(position);

        holder.title.setText(title);
        List<ProDetails.RecordListBean> data = list.get(title);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerView.setAdapter(adaptyer = new ScircuititemAdaptyer(R.layout.adapter_scircuit_content_list, data));

    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 1;
        } else {
            int count = list.size() + 2;
            return count;
        }
    }

    //头部
    public class TypeHeadViewHolder extends RecyclerView.ViewHolder {
        public TypeHeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    //头部
    public class TypeEmptyViewHolder extends RecyclerView.ViewHolder {
        public TypeEmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    //内容
    public class TypeDataViewHolder extends RecyclerView.ViewHolder {
        TextView title, datatime;
        RecyclerView recyclerView;

        public TypeDataViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            datatime = itemView.findViewById(R.id.datatime);
            recyclerView = itemView.findViewById(R.id.scircuit_content_list);
        }
    }

    //尾部
    public class TypeEndViewHolder extends RecyclerView.ViewHolder {

        public TypeEndViewHolder(View itemView) {
            super(itemView);


        }
    }


    //设置展示的type
    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0) {
            return EMPTYVIEW;
        } else {
            int contentSize = list.size() + 1;
            if (position == 0) {
                // 头部
                return TYPE_HEARD;
            } else if (position == contentSize) {
                // 尾部
                return TYPE_END;
            } else {
                //内容
                return HEAD_COUNT;
            }
        }

    }

    /**
     * item显示动画
     *
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) {
            return;
        }
        //animationsLocked是布尔类型变量，一开始为false
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

    public void setNewData(Map<String, ArrayList<ProDetails.RecordListBean>> lsit, ArrayList<String> titlelist) {
        this.list = lsit;
        this.titlelist = titlelist;
        notifyDataSetChanged();
    }


}
