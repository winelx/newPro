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
import com.example.administrator.newsdf.activity.home.AllListmessageActivity;
import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.callback.CallBackUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.LeftSlideView;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 全部消息界面的适配器
 */
public class AllMessageAdapter extends RecyclerView.Adapter<AllMessageAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Home_item> mDatas = new ArrayList<Home_item>();

    private LeftSlideView mMenu = null;
    private int Unfinish;
    private static final int MAX = 99;

    public AllMessageAdapter(Context context) {
        mContext = context;

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);
        if (mDatas.get(position).isPutTop()) {
            holder.btn_Delete.setText("取消置顶");
        } else {
            holder.btn_Delete.setText("置顶");
        }

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
        //判断是否有消息
        String message = mDatas.get(position).getUnfinish();
        try {
            Unfinish = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (Unfinish > MAX) {
            holder.home_item_message.setText("99+");
        } else {
            holder.home_item_message.setText(mDatas.get(position).getUnfinish());
        }

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
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatas.get(position).isPutTop()) {
                    List<Shop> list = new ArrayList<Shop>();
                    list = LoveDao.ALLCart();
                    // 状态为ture 为置顶状态 点击为取消
                    //删除置顶
                    String str = mDatas.get(position).getId();
                    for (int i = 0; i < list.size(); i++) {
                        String wbsID = list.get(i).getWebsid();
                        if (str.equals(wbsID)) {
                            LoveDao.deleteLove(list.get(i).getId());
                            CallBackUtils.removeCallBackMethod(position, "删除");
                        }
                    }
                } else {
                    //状态为false 点击为置顶
                    //添加置顶
                    Shop shop = new Shop();
                    //保存ID
                    shop.setWebsid(mDatas.get(position).getId());
                    shop.setType(Shop.TYPE_ALL);
                    LoveDao.insertLove(shop);
                    CallBackUtils.removeCallBackMethod(position, "增加");
                }
                closeMenu();
            }
        });
        holder.tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加收藏
                OkGo.post(Requests.WBSSAVE)
                        .params("wbsId", mDatas.get(position).getOrgid())
                        .params("type", 1)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int ret = jsonObject.getInt("ret");
                                    ToastUtils.showLongToast(jsonObject.getString("msg"));
                                    if (ret==0){
                                        holder.tv_set.setBackgroundResource(R.color.back);
                                        holder.tv_set.setText("已收藏");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_fragment_item, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
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
            ((LeftSlideView) itemView).setSlidingButtonListener(AllMessageAdapter.this);
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


}