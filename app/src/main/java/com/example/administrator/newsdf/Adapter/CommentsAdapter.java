package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.MainActivity;
import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.utils.LeftSlideView;
import com.example.administrator.newsdf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Home_item> mDatas = new ArrayList<Home_item>();

    private LeftSlideView mMenu = null;

    public CommentsAdapter(Context context) {
        mContext = MainActivity.getInstance();
    }

    int number;
    private static final int MAX = 99;

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);
        if (mOnItemClickListener != null) {
            holder.layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        /**
         *
         *
         * 有状态未添加
         */
        //判断是否有消息
        holder.home_item_message.setVisibility(View.GONE);
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

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment_item, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView btn_Delete;
        public RelativeLayout layout_content;
        public RelativeLayout relativeLayout;
        public TextView home_item_img;
        public TextView home_item_name;
        public TextView home_item_content;
        public TextView home_item_time;
        public TextView home_item_message;

        public MyViewHolder(View itemView) {
            super(itemView);
            //统一自定义控件获标签，都是tv_delete，修改后无法获取，会报错 如果非要修改，请全部修改，
            //置顶
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
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
        }
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

    public void getData(List<Home_item> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }



    private OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}