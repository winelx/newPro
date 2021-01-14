package com.example.administrator.fengji.pzgc.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.activity.ChecknoticeMessagelistActivity;
import com.example.administrator.fengji.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.fengji.pzgc.utils.LeftSlideView;
import com.example.administrator.fengji.pzgc.utils.SlantedTextView;
import com.example.administrator.fengji.pzgc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里未上传资料的recycler的适配器
 */
public class CheckRectifyMessageAdapter extends RecyclerView.Adapter<CheckRectifyMessageAdapter.MyViewHolder>
        implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<MyNoticeDataBean> mDatas = new ArrayList<>();

    private LeftSlideView mMenu = null;

    public CheckRectifyMessageAdapter(Context context) {
        mContext = context;

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (menuIsOpen()) {
            closeMenu();//关闭菜单
        }
        //设置内容布局的宽为屏幕宽度
        holder.subLayoutContent.getLayoutParams().width = Utils.getScreenWidth(mContext);
        holder.managementTitle.setText(mDatas.get(position).getPartDetails());
        holder.managementUser.setText("下发人：" + mDatas.get(position).getCheckPersonName() + "   " + mDatas.get(position).getUpdateDate());
        holder.managementwbs.setText(mDatas.get(position).getRectificationOrgName());
        holder.managementTitle.setText(mDatas.get(position).getStandardDelName());
        holder.managementOrg.setText("检查组织：" + mDatas.get(position).getCheckOrgName());
        String score = mDatas.get(position).getStandardDelScore();
        if (!score.isEmpty()) {
            holder.managementNumber.setText(setText("扣分：" + mDatas.get(position).getStandardDelScore(), 3));
        } else {
            holder.managementNumber.setText("扣分：" + mDatas.get(position).getStandardDelScore());
            holder.managementNumber.setTextColor(Color.parseColor("#000000"));
        }
        holder.noticeUser.setText("整改负责人：" + mDatas.get(position).getNoticeuser());
        holder.noticeLasttime.setText("整改期限：" + mDatas.get(position).getNoticetime());
        Boolean isDeal = mDatas.get(position).isDeal();
        if (isDeal) {
            holder.tvDelete.setVisibility(View.GONE);
            holder.tvSet.setVisibility(View.GONE);
            String motionNode = mDatas.get(position).getMotionNode();
            if ("5".equals(motionNode)) {
                holder.infaceItemMessage.setTextString("已完成");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);
            } else {
                holder.infaceItemMessage.setTextString("已处理");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);
            }
        } else {
            holder.tvDelete.setVisibility(View.GONE);
            String status = mDatas.get(position).getStatus();
            if ("3".equals(status)) {
                holder.infaceItemMessage.setTextString("打回");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.red);
            } else {
                String motionNode = mDatas.get(position).getMotionNode();
                if (motionNode.isEmpty()) {
                    holder.infaceItemMessage.setTextString("未下发");
                    holder.tvDelete.setVisibility(View.VISIBLE);
                    holder.tvSet.setVisibility(View.VISIBLE);
                    holder.infaceItemMessage.setSlantedBackgroundColor(R.color.graytext);
                } else {
                    if ("1".equals(motionNode) || "0".equals(motionNode)) {
                        holder.infaceItemMessage.setTextString("未回复");
                        holder.tvSet.setVisibility(View.GONE);
                        holder.infaceItemMessage.setSlantedBackgroundColor(R.color.Orange);

                    } else if ("2".equals(motionNode) || "3".equals(motionNode)) {
                        holder.infaceItemMessage.setTextString("未验证");
                        holder.tvSet.setVisibility(View.GONE);
                        holder.infaceItemMessage.setSlantedBackgroundColor(R.color.Orange);

                    } else if ("5".equals(motionNode)) {
                        holder.infaceItemMessage.setTextString("已完成");
                        holder.tvSet.setVisibility(View.GONE);
                        holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);

                    } else {

                    }

                }
            }
        }

        //item正文点击事件
        holder.subLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    ChecknoticeMessagelistActivity activity = (ChecknoticeMessagelistActivity) mContext;
                    activity.status(holder.infaceItemMessage.getText(), mDatas.get(position).getNoticeId(), position);

                }
            }
        });

        //左滑删除点击事件
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("是否删除本条整改通知");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChecknoticeMessagelistActivity activity = (ChecknoticeMessagelistActivity) mContext;
                        activity.detele(position);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        closeMenu();
                    }
                });
                builder.show();


            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.check_notice_message, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSet, tvDelete;
        public RelativeLayout subLayoutContent;
        private TextView managementTitle, managementUser, managementOrg, managementNumber, managementwbs;
        private TextView noticeUser, noticeLasttime;
        private SlantedTextView infaceItemMessage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvSet = itemView.findViewById(R.id.tv_set);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            managementwbs = itemView.findViewById(R.id.management_wbs);
            infaceItemMessage = itemView.findViewById(R.id.inface_item_message);
            subLayoutContent = itemView.findViewById(R.id.sub_layout_content);
            managementTitle = itemView.findViewById(R.id.management_title);
            managementUser = itemView.findViewById(R.id.management_user);
            managementOrg = itemView.findViewById(R.id.management_org);
            managementNumber = itemView.findViewById(R.id.management_number);
            noticeUser = itemView.findViewById(R.id.notice_user);
            noticeLasttime = itemView.findViewById(R.id.notice_lasttime);

            ((LeftSlideView) itemView).setSlidingButtonListener(CheckRectifyMessageAdapter.this);
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
        return mMenu != null;
    }

    public void getData(List<MyNoticeDataBean> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

    private SpannableString setText(String str, int num) {
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                num,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), num + 1,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    public void getnoti(int pos) {
        notifyItemChanged(pos);
    }
}