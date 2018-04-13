package com.example.administrator.newsdf.Adapter;

/**
 * Created by Administrator on 2017/12/27 0027.
 */


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
import com.example.administrator.newsdf.activity.home.LightfaceActivity;
import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.callback.CallBackUtils;
import com.example.administrator.newsdf.utils.LeftSlideView;
import com.example.administrator.newsdf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里未上传资料的recycler的适配器
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Home_item> mDatas = new ArrayList<Home_item>();

    private LeftSlideView mMenu = null;

    public HomeFragmentAdapter(Context context) {
        mContext = context;
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
        //item正文点击事件
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    Intent intent = new Intent(mContext, LightfaceActivity.class);
                    intent.putExtra("name", mDatas.get(position).getOrgname());
                    intent.putExtra("back", mDatas.get(position).getOrgname());
                    intent.putExtra("orgId", mDatas.get(position).getOrgid());
                    mContext.startActivity(intent);
                }
            }
        });
        //左滑置顶点击事件
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建集合
                List<Shop> list =new  ArrayList<Shop>();
                //拿到数据
                list= LoveDao.MineCart();
               if (mDatas.get(position).isPutTop()){
                    // 状态为ture 为置顶状态 点击为取消
                    //拿到当前item的ID
                    String str=mDatas.get(position).getId();
                    //便利数据库数据
                    for (int i = 0; i <list.size() ; i++) {
                        //拿到数据库ID
                        String wbsID=list.get(i).getWebsid();
                        //数据库ID与当前节点id
                        if (str.equals(wbsID)){
                            //相等就删除

                            LoveDao.deleteLove(list.get(i).getId());
                            CallBackUtils.dohomeCallBackMethod();
                        }
                    }
                }else {
                    //状态为false 点击为置顶
                    //添加置顶
                    Shop shop=new Shop();
                    //保存ID
                   shop.setWebsid(mDatas.get(position).getId());
                   shop.setType(Shop.TYPE_MINE);
                   LoveDao.insertLove(shop);

                   CallBackUtils.dohomeCallBackMethod();
                }
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
            }
        });
        if (mDatas.get(position).isPutTop()){
            holder.btn_Delete.setText("取消置顶");
        }else {
            holder.btn_Delete.setText("置顶");
        }

        /**
         *
         *
         * 有状态未添加
         */
        //判断是否有消息
        String mess = mDatas.get(position).getUnfinish();
        if (mess.length() == 0) {
            holder.home_item_message.setVisibility(View.INVISIBLE);
            holder.home_item_message.setText(mess);
        }
        int Random = (int) (Math.random() * 4) + 1;
        if (Random == 1) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_blue);
        } else if (Random == 2) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_yello);
        } else if (Random == 3) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_style);
        } else if (Random == 4) {
            holder.home_item_img.setBackgroundResource(R.drawable.homt_item_blue);
        }
        //前面圆圈
        holder.home_item_img.setText(mDatas.get(position).getOrgname());
        //所属组织
        holder.home_item_name.setText(mDatas.get(position).getOrgname());
        //最后一条消息
        holder.home_item_content.setText(mDatas.get(position).getContent());
        //最后一条消息时间
        holder.home_item_time.setText(mDatas.get(position).getCreaeTime());
        String str = mDatas.get(position).getUnfinish();
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (number > MAX) {
            holder.home_item_message.setText("99+");
        } else {
            holder.home_item_message.setText(mDatas.get(position).getUnfinish());
        }
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
            //统一自定义控件获标签，都是tv_delete，修改后无法获取，会报错
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
            ((LeftSlideView) itemView).setSlidingButtonListener(HomeFragmentAdapter.this);
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

    public void getData(List<Home_item> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

}