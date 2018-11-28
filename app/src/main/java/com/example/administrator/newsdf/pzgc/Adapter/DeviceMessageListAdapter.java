package com.example.administrator.newsdf.pzgc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.ChecknoticeMessagelistActivity;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceMessageListActivity;
import com.example.administrator.newsdf.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.newsdf.pzgc.utils.LeftSlideView;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;
import com.example.administrator.newsdf.pzgc.utils.Utils;

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

    private List<MyNoticeDataBean> mDatas = new ArrayList<>();

    private LeftSlideView mMenu = null;

    public DeviceMessageListAdapter(Context context) {
        mContext = context;

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

        Boolean isDeal = mDatas.get(position).isDeal();
        if (isDeal) {
            //不能删除
            holder.tvDelete.setVisibility(View.GONE);
            holder.tvSet.setVisibility(View.GONE);
        } else {
            //能删除
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvSet.setVisibility(View.VISIBLE);

        }

        //item正文点击事件
        holder.subLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    //
                    ToastUtils.showLongToast("sss");
//                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    DeviceMessageListActivity activity = (DeviceMessageListActivity) mContext;
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

    public void getData(List<MyNoticeDataBean> shops) {
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