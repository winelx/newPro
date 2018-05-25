package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.AllListmessageActivity;
import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.callback.CollectionCallbackUtils;
import com.example.administrator.newsdf.utils.LeftSlideView;
import com.example.administrator.newsdf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/21 0021.
 * 首页收藏界面适配器
 */

public class CollectionFrAdapter extends RecyclerView.Adapter<CollectionFrAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private Context mContext;

    private List<Home_item> mDatas = new ArrayList<Home_item>();

    private LeftSlideView mMenu = null;
    private int Unfinish;
    private static final int MAX = 99;

    public CollectionFrAdapter(Context context) {
        mContext = context;

    }

    /**
     * 初始化界面
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_fragment_item, parent, false);
        CollectionFrAdapter.MyViewHolder holder = new CollectionFrAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);
        //item正文点击事件
        holder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    Intent intent = new Intent(mContext, AllListmessageActivity.class);
                    intent.putExtra("name", mDatas.get(position).getOrgname());
                    intent.putExtra("back", mDatas.get(position).getOrgname());
                    intent.putExtra("orgId", mDatas.get(position).getOrgid());
                    mContext.startActivity(intent);
                }
            }
        });
        //左滑置顶点击事件
        holder.tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Shop> list = new ArrayList<Shop>();
                list = LoveDao.MineHide();
                // 状态为ture 为置顶状态 点击为取消
                //删除置顶
                String str = mDatas.get(position).getId();
                for (int i = 0; i < list.size(); i++) {
                    String wbsID = list.get(i).getCheckid();
                    if (str.equals(wbsID)) {
                        LoveDao.deleteLove(list.get(i).getId());
                    }
                }
                mDatas.remove(position);
                CollectionCallbackUtils.removeCallBackMethod();
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                notifyDataSetChanged();
            }
        });
        holder.btn_Delete.setVisibility(View.GONE);
        holder.tv_set.setBackgroundResource(R.color.back);
        holder.tv_set.setText("已收藏");
        //随机数，改变标段的颜色
        int Random = (int) (Math.random() * 4) + 1;
        if (Random == 1) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_blue);
        } else if (Random == 2) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_yello);
        } else if (Random == 3) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_style);
        } else if (Random == 4) {
            holder.home_item_img.setBackgroundResource(R.drawable.homt_item_green);
        }
        //前面圆圈
        holder.home_item_img.setText(mDatas.get(position).getOrgname());
        //所属组织
        holder.home_item_name.setText(mDatas.get(position).getOrgname());
        //最后一条消息
        holder.home_item_content.setText(mDatas.get(position).getContent());
        //最后一条消息时间
        holder.home_item_time.setText(mDatas.get(position).getCreaeTime());
        //消息量
        holder.home_item_message.setText(mDatas.get(position).getUnfinish());
        holder.home_item_message.setVisibility(View.GONE);
        //判断是否有消息
        String message = mDatas.get(position).getUnfinish();
        try {
            Unfinish = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete, tv_set;
        public RelativeLayout layout_content;
        public RelativeLayout relativeLayout;
        public TextView home_item_img;
        public TextView home_item_name;
        public TextView home_item_content;
        public TextView home_item_time;
        public TextView home_item_message;

        public MyViewHolder(View itemView) {
            super(itemView);
            //置顶
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_set = (TextView) itemView.findViewById(R.id.tv_set);

            //控制布局在界面的宽度
            layout_content = itemView.findViewById(R.id.layout_content);
            //控制布局大小
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            //标段
            home_item_img = (TextView) itemView.findViewById(R.id.home_item_img);
            //所属组织
            home_item_name = (TextView) itemView.findViewById(R.id.home_item_name);
            //推送内容
            home_item_content = (TextView) itemView.findViewById(R.id.home_item_content);
            //更新时间
            home_item_time = (TextView) itemView.findViewById(R.id.home_item_time);
            //消息数据
            home_item_message = (TextView) itemView.findViewById(R.id.home_item_message);
            ((LeftSlideView) itemView).setSlidingButtonListener(CollectionFrAdapter.this);
        }
    }

    public void getData(List<Home_item> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

}
