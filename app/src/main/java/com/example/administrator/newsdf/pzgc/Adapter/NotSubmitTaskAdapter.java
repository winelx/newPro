package com.example.administrator.newsdf.pzgc.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckListDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTasklistActivity;
import com.example.administrator.newsdf.pzgc.bean.CheckTasklistAdapter;
import com.example.administrator.newsdf.pzgc.utils.LeftSlideView;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里未上传资料的recycler的适配器
 */
public class NotSubmitTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_SUB = 1;
    private static final int TYPE_END = 2;
    private Context mContext;

    private List<Object> mDatas = new ArrayList<>();

    private LeftSlideView mMenu = null;

    public NotSubmitTaskAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SUCCESS:
                return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_management, parent, false));
            case TYPE_SUB:
                return new SubViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.check_management_sub, parent, false));
            case TYPE_END:
                return new RecyclerHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_null_layout, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Object obj = mDatas.get(position);
        if (holder instanceof MyViewHolder) {
            final CheckTasklistAdapter success = (CheckTasklistAdapter) obj;
            //标题
            ((MyViewHolder) holder).managementTitle.setText(success.getWbsMainName());
            //
            ((MyViewHolder) holder).managementBlock.setText("所属标段：" + success.getOrgName());

            ((MyViewHolder) holder).managementOrg.setText("检查组织：" + success.getCheckOrgName());
            //分数
            ((MyViewHolder) holder).managementNumber.setText(setText("总分:" + success.getScore()));
            ((MyViewHolder) holder).managementUser.setText("检查人:" + success.getCheckUser() + "   " + success.getCreateDate());
            ((MyViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CheckListDetailsActivity.class);
                    intent.putExtra("id", success.getId());
                    mContext.startActivity(intent);
                }
            });


        } else if (holder instanceof SubViewHolder) {
            final SCheckTasklistAdapter Sub = (SCheckTasklistAdapter) obj;
            //动态设置字项item宽度(嵌套层次太深，无法获取父级宽度)
            //设置内容布局的宽为屏幕宽度
            ((SubViewHolder) holder).layoutContent.getLayoutParams().width = Utils.getScreenWidth(mContext) - 80;
            ((SubViewHolder) holder).managementTitle.setText(Sub.getWbsMainName());
            ((SubViewHolder) holder).managementUser.setText(Sub.getCheckUser() + "      " + Sub.getCreateDate());
            ((SubViewHolder) holder).sub_management_block.setText("所属标段：" + Sub.getOrgName());
            ((SubViewHolder) holder).subManagementOrg.setText("检查组织:" + Sub.getCheckOrgName());
            ((SubViewHolder) holder).slantedTextView.setTextString("未提交");
            ((SubViewHolder) holder).slantedTextView.setSlantedBackgroundColor(R.color.unfinish_gray);
            ((SubViewHolder) holder).tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckTasklistActivity addActivity = (CheckTasklistActivity) mContext;
                    addActivity.delete(position, Sub.getId());
                }
            });
            ((SubViewHolder) holder).layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckTasklistActivity addActivity = (CheckTasklistActivity) mContext;
                    addActivity.submit(Sub.getId());
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mDatas.get(position) instanceof CheckTasklistAdapter) {
            return TYPE_SUCCESS;
        } else if (mDatas.get(position) instanceof SCheckTasklistAdapter) {
            return TYPE_SUB;
        } else if (mDatas.get(position) instanceof String) {
            return TYPE_END;
        } else {
            return super.getItemViewType(position);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView managementTitle, managementUser;
        private TextView managementNumber, managementBlock, managementOrg, tvSet;
        private RelativeLayout item;

        MyViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_not);
            managementBlock = itemView.findViewById(R.id.management_block);
            managementOrg = itemView.findViewById(R.id.management_org);
            managementNumber = itemView.findViewById(R.id.management_number);
            managementUser = itemView.findViewById(R.id.management_user);
            managementTitle = itemView.findViewById(R.id.management_title);
        }
    }

    class SubViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutContent;
        private TextView managementTitle, managementUser, subManagementOrg;
        private TextView tvDelete, sub_management_block;
        private SlantedTextView slantedTextView;

        SubViewHolder(View itemView) {
            super(itemView);
            subManagementOrg = itemView.findViewById(R.id.sub_management_org);
            sub_management_block = itemView.findViewById(R.id.sub_management_block);
            slantedTextView = itemView.findViewById(R.id.sub_inface_item_message);
            managementUser = itemView.findViewById(R.id.sub_management_user);
            managementTitle = itemView.findViewById(R.id.sub_management_title);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            layoutContent = itemView.findViewById(R.id.sub_layout_content);
            ((LeftSlideView) itemView).setSlidingButtonListener(NotSubmitTaskAdapter.this);
        }
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        public RecyclerHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 删除item
     *
     * @param position
     */
    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param leftSlideView
     */
    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    public void getData(List<Object> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String status);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private SpannableString setText(String str) {
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), 3,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }
}