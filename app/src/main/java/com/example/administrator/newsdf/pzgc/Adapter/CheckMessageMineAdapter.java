package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.utils.LeftSlideView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class CheckMessageMineAdapter extends RecyclerView.Adapter<CheckMessageMineAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Home_item> mDatas;

    private LeftSlideView mMenu = null;

    public CheckMessageMineAdapter(Context context, List<Home_item> mDatas) {
        mContext = MainActivity.getInstance();
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.check_me_title.setText();
        int number;
        try {
             number = Integer.parseInt(mDatas.get(position).getUnfinish());
        }catch (NumberFormatException e){
            number=0;
        }

        if (number > 0) {
            holder.homeItemMessage.setText(number + "");
            holder.homeItemMessage.setVisibility(View.VISIBLE);
        } else {
            holder.homeItemMessage.setVisibility(View.GONE);
        }
        holder.checkMeTitle.setText(mDatas.get(position).getOrgname());
        holder.homeItemImg.setText(mDatas.get(position).getParentname());
        holder.check_relati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_checkdown_me, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView homeItemImg,
                checkMeTitle,
                homeItemMessage;
        RelativeLayout check_relati;

        public MyViewHolder(View itemView) {
            super(itemView);
            //统一自定义控件获标签，都是tv_delete，修改后无法获取，会报错 如果非要修改，请全部修改，
            homeItemImg = itemView.findViewById(R.id.home_item_img);
            checkMeTitle = itemView.findViewById(R.id.check_me_title);
            homeItemMessage = itemView.findViewById(R.id.home_item_message);
            check_relati = itemView.findViewById(R.id.check_relati);
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

    //声明接口
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}