package com.example.administrator.newsdf.pzgc.activity.changed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedListActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单列表适配器
 * {@link  ChagedListActivity}
 */
public class ChagedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChagedList> list;
    private Context mContext;

    private static final int TYPE_HEARD = 1;
    private static final int TYPE_DATA = 2;

    public ChagedListAdapter(ArrayList<ChagedList> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new TypeContent(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.adapter_item_chaged_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeContent) {
            bindcontent((TypeContent) holder, list, position);
        }
    }

    private void bindcontent(final TypeContent holder, ArrayList<ChagedList> list, final int position) {
        if (position == 4) {
            //setIos 添加阻尼效果，//setLeftSwipe是否开启侧滑
            holder.swipmenulayout.setIos(true).setLeftSwipe(false);
        } else {
            holder.swipmenulayout.setIos(true).setLeftSwipe(true);
        }
        holder.chageeListContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.onClick(position);
            }
        });
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipmenulayout.quickClose();
                mOnSwipeListener.onDel(position, holder.swipmenulayout);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    class TypeContent extends RecyclerView.ViewHolder {
        private SlantedTextView infaceItemMessage;
        private LinearLayout chageeListContent;
        private Button item_delete;
        private SwipeMenuLayout swipmenulayout;

        public TypeContent(View itemView) {
            super(itemView);
            infaceItemMessage = itemView.findViewById(R.id.inface_item_message);
            swipmenulayout = itemView.findViewById(R.id.swipmenu);
            chageeListContent = itemView.findViewById(R.id.chagee_list_content);
            item_delete = itemView.findViewById(R.id.item_delete);
        }
    }

    class TypeExpty extends RecyclerView.ViewHolder {


        public TypeExpty(View itemView) {
            super(itemView);

        }
    }

    public void setNewData(ArrayList<ChagedList> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public interface onSwipeListener {
        void onDel(int pos, SwipeMenuLayout layout);

        void onClick(int pos);
    }

    private onSwipeListener mOnSwipeListener;


    public void setOnDelListener(onSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }


}
