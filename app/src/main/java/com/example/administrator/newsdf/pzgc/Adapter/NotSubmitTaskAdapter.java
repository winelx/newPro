package com.example.administrator.newsdf.pzgc.Adapter;


import android.content.Context;
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
import com.example.administrator.newsdf.camera.ToastUtils;
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
public class NotSubmitTaskAdapter extends RecyclerView.Adapter<NotSubmitTaskAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<CheckTasklistAdapter> mDatas = new ArrayList<CheckTasklistAdapter>();

    private LeftSlideView mMenu = null;

    public NotSubmitTaskAdapter(Context context) {
        mContext = context;
        activity = (CheckTasklistActivity) mContext;
    }

    private CheckTasklistActivity activity;

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext) - 80;
        //时间
        holder.management_user.setText(mDatas.get(position).getUserdata());
        holder.managementNumber.setText(setText("总分：" + mDatas.get(position).getNumber()));
        holder.managementBlock.setText("所属标段:" + mDatas.get(position).getBlock());
        holder.managementOrg.setText("所属组织:" + mDatas.get(position).getOrg());
        //左滑删除点击事件
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShortToastCenter(position + "");
                removeData(position);
            }
        });
        String status = mDatas.get(position).getStatus();
        if (status.equals("1")) {
            holder.slantedTextView.setTextString("已提交");
            holder.tvDelete.setVisibility(View.GONE);
            holder.tv_set.setVisibility(View.GONE);
            holder.slantedTextView.setSlantedBackgroundColor(R.color.finish_green);
        } else {
            //状态
            holder.slantedTextView.setTextString("未提交");
            //状态 finish_green
            holder.slantedTextView.setSlantedBackgroundColor(R.color.gray);
        }
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position = holder.getLayoutPosition();
                    // 2
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.check_management, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public RelativeLayout layout_content;
        private TextView managementTitle, management_user;
        private TextView tvDelete, managementNumber, managementBlock, managementOrg, tv_set;
        private SlantedTextView slantedTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            managementBlock = itemView.findViewById(R.id.management_block);
            managementOrg = itemView.findViewById(R.id.management_org);
            managementNumber = itemView.findViewById(R.id.management_number);
            slantedTextView = itemView.findViewById(R.id.inface_item_message);
            management_user = itemView.findViewById(R.id.management_user);
            managementTitle = itemView.findViewById(R.id.management_title);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tv_set = itemView.findViewById(R.id.tv_set);
            layout_content = itemView.findViewById(R.id.layout_content);
            ((LeftSlideView) itemView).setSlidingButtonListener(NotSubmitTaskAdapter.this);
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

    public void getData(List<CheckTasklistAdapter> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

    private SpannableString setText(String str) {
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), 3,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}