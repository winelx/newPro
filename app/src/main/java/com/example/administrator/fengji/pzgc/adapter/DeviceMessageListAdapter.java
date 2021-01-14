package com.example.administrator.fengji.pzgc.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.administrator.fengji.pzgc.bean.DeviceMeList;
import com.example.administrator.fengji.pzgc.inter.ItemClickListener;
import com.example.administrator.fengji.pzgc.utils.LeftSlideView;
import com.example.administrator.fengji.pzgc.utils.SlantedTextView;
import com.example.administrator.fengji.pzgc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/28 0028.
 * @description:
 */

public class DeviceMessageListAdapter extends RecyclerView.Adapter<DeviceMessageListAdapter.MyViewHolder>
        implements LeftSlideView.IonSlidingButtonListener {
    private Context mContext;

    private List<DeviceMeList> mDatas = new ArrayList<>();

    private LeftSlideView mMenu = null;

    public DeviceMessageListAdapter(Context context) {
        mContext = context;

    }

    private ItemClickListener oClickListener;

    public void setOnItemClickListener(ItemClickListener mOnItemClickListener) {
        this.oClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final DeviceMessageListAdapter.MyViewHolder holder, final int position) {
        if (menuIsOpen()) {
            closeMenu();//关闭菜单
        }
        //设置内容布局的宽为屏幕宽度
        holder.subLayoutContent.getLayoutParams().width = Utils.getScreenWidth(mContext);
        holder.deviceMessageCreatename.setText("巡检人：" + mDatas.get(position).getCheckUserName());
        holder.deviceMessageCreatedata.setText("巡检日期：" + mDatas.get(position).getCheck_date());
        holder.deviceMessageHead.setText("整改负责人：" + mDatas.get(position).getPersonLiableName());
        holder.deviceMessageOrg.setText("巡检组织：" + mDatas.get(position).getCheckOrgName());
        //编号
        holder.deviceMessageNumber.setText(mDatas.get(position).getNumber());
        //设备名称
        holder.device_message_title.setText(mDatas.get(position).getTypeName());
        int status = mDatas.get(position).getStatus();
        if (status == 0) {
            //能删除
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvSet.setVisibility(View.VISIBLE);
        } else {
            //不能删除
            holder.tvDelete.setVisibility(View.GONE);
            holder.tvSet.setVisibility(View.GONE);
        }
        switch (status) {
            case 0:
                holder.infaceItemMessage.setTextString("未下发");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.gray);
                break;
            case 1:
                holder.infaceItemMessage.setTextString("未回复");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.Orange);
                break;
            case 2:
                holder.infaceItemMessage.setTextString("未验证");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.Orange);
                break;
            case 3:
                holder.infaceItemMessage.setTextString("打回");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.red);
                break;
            case 4:
                holder.infaceItemMessage.setTextString("已处理");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);
                break;
            case 5:
                holder.infaceItemMessage.setTextString("完成");
                holder.infaceItemMessage.setSlantedBackgroundColor(R.color.finish_green);
                break;
            default:
                break;


        }

        //item正文点击事件
        holder.subLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int position = holder.getLayoutPosition();
                    oClickListener.Onclick(holder.itemView, position);
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
                        oClickListener.ondelete(position);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_device_messagelist, arg0, false);
        DeviceMessageListAdapter.MyViewHolder holder = new DeviceMessageListAdapter.MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSet, title, tvDelete;
        public RelativeLayout subLayoutContent;
        //编号
        private TextView deviceMessageNumber;
        //标题
        private TextView device_message_title;
        //巡检组织
        private TextView deviceMessageOrg;
        //整改负责人
        private TextView deviceMessageHead;
        //巡检人 巡检日期
        private TextView deviceMessageCreatedata, deviceMessageCreatename;
        //角标
        private SlantedTextView infaceItemMessage;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView) {
            super(itemView);
            tvSet = itemView.findViewById(R.id.tv_set);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            subLayoutContent = itemView.findViewById(R.id.device_layout_content);
            infaceItemMessage = itemView.findViewById(R.id.inface_item_message);
            device_message_title = itemView.findViewById(R.id.device_message_title);
            deviceMessageNumber = itemView.findViewById(R.id.device_message_number);
            deviceMessageOrg = itemView.findViewById(R.id.device_message_org);
            deviceMessageHead = itemView.findViewById(R.id.device_message_head);
            deviceMessageCreatedata = itemView.findViewById(R.id.device_message_createdata);
            deviceMessageCreatename = itemView.findViewById(R.id.device_message_createname);
            ((LeftSlideView) itemView).setSlidingButtonListener(DeviceMessageListAdapter.this);
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

    public void getData(List<DeviceMeList> shops) {
        mDatas = shops;

        notifyDataSetChanged();
    }

    //设置文字颜色
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

    //刷新状态
    public void getnoti(int pos) {
        notifyItemChanged(pos);
    }


}