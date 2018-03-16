package com.example.administrator.newsdf.adapter;

/**
 * Created by Administrator on 2017/12/27 0027.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.UploadPhotoActivity;
import com.example.administrator.newsdf.utils.LeftSlideView;
import com.example.administrator.newsdf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载图片recycler的适配器
 */
public class UploadPhAdapter extends RecyclerView.Adapter<UploadPhAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Shop> mDatas = new ArrayList<Shop>();

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private IonSlidingViewClickListener mISetBtnClickListener;

    private LeftSlideView mMenu = null;

    public UploadPhAdapter(Context context) {
        mContext = context;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mISetBtnClickListener = (IonSlidingViewClickListener) context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);

        //item正文点击事件
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    UploadPhotoActivity activity = (UploadPhotoActivity) mContext;
                    activity.getInt(position);
                }
            }
        });
        //左滑设置点击事件
        holder.btn_Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = holder.getLayoutPosition();
                mISetBtnClickListener.onSetBtnCilck(view, n);
            }
        });

        //左滑删除点击事件
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPhotoActivity activity = (UploadPhotoActivity) mContext;
                activity.deleteDate(position);
            }
        });
        holder.up_ph_name.setText(mDatas.get(position).getContent());
        holder.up_ph_data.setText(mDatas.get(position).getTimme());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.up_ph_item, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView btn_Set;
        public TextView btn_Delete;
        public TextView up_ph_data, up_ph_name;
        public RelativeLayout layout_content;
        public LinearLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            btn_Set =itemView.findViewById(R.id.tv_set);
            btn_Delete =  itemView.findViewById(R.id.tv_delete);
            layout_content =  itemView.findViewById(R.id.layout_content);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            up_ph_data=(TextView) itemView.findViewById(R.id.up_ph_data);
            up_ph_name=(TextView) itemView.findViewById(R.id.up_ph_name);
            ((LeftSlideView) itemView).setSlidingButtonListener(UploadPhAdapter.this);
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

    public void getData(List<Shop> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {

        void onDeleteBtnCilck(View view, int position);//点击“删除”

        void onSetBtnCilck(View view, int position);//点击“设置”
    }


}