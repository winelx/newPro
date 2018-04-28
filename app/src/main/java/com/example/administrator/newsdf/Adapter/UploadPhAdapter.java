package com.example.administrator.newsdf.Adapter;

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
import java.util.Arrays;
import java.util.List;


/**
 * description: 离线图片recycler的适配器
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:46
 *         update: 2018/3/22 0022
 *         version:
 */
public class UploadPhAdapter extends RecyclerView.Adapter<UploadPhAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Shop> mDatas = new ArrayList<>();

    private LeftSlideView mMenu = null;

    public UploadPhAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //设置内容布局的宽为屏幕宽度
        holder.layoutContent.getLayoutParams().width = Utils.getScreenWidth(mContext);
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

        //左滑删除点击事件
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPhotoActivity activity = (UploadPhotoActivity) mContext;
                activity.deleteDate(position);
            }
        });
        List<String> list = stringToList(mDatas.get(position).getContent());

        holder.photo_name.setText(list.get(0));
        holder.photo_number.setText(list.get(1));
        holder.photo_names.setText(list.get(2));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.up_ph_item, arg0, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView btnSet;
        TextView btnDelete;
        TextView photo_name, photo_number, photo_names;
        RelativeLayout layoutContent;
        LinearLayout relativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);

            btnSet = itemView.findViewById(R.id.tv_set);
            btnDelete = itemView.findViewById(R.id.tv_delete);
            layoutContent = itemView.findViewById(R.id.layout_content);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            photo_name = itemView.findViewById(R.id.photo_name);
            photo_number = itemView.findViewById(R.id.photo_number);
            photo_names = itemView.findViewById(R.id.photo_names);
            ((LeftSlideView) itemView).setSlidingButtonListener(UploadPhAdapter.this);
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
     */
    private Boolean menuIsOpen() {
        return mMenu != null;
    }

    public void getData(List<Shop> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

    /**
     * string转集合
     */
    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {

        } else {
            String str[] = strs.split(">>");
            return Arrays.asList(str);
        }
        return null;
    }

}