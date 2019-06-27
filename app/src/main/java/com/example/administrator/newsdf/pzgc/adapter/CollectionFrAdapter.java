package com.example.administrator.newsdf.pzgc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.callback.CallBackUtils;
import com.example.administrator.newsdf.pzgc.callback.HideCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.frehomeCallBackUtils;
import com.example.administrator.newsdf.pzgc.utils.LeftSlideView;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/21 0021.
 * 首页收藏界面适配器
 */

public class CollectionFrAdapter extends RecyclerView.Adapter<CollectionFrAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private Context mContext;
    private List<Home_item> mDatas;
    private LeftSlideView mMenu = null;


    public CollectionFrAdapter(Context context, List<Home_item> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
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
        if (mOnItemClickListener != null) {
            holder.layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        holder.btn_Delete.setVisibility(View.GONE);
        holder.tv_set.setBackgroundResource(R.color.red);
        holder.tv_set.setText("取消收藏");
        //收藏点击事件
        holder.tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.get(Requests.delete)
                        .params("wbsId", mDatas.get(position).getOrgid())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int ret = jsonObject.getInt("ret");
                                    if (ret == 0) {
                                        ToastUtils.showLongToast("取消成功");
                                        mDatas.remove(position);
                                        notifyDataSetChanged();
                                        //刷新全部
                                        CallBackUtils.removeCallBackMethod();
                                        //刷新我的
                                        frehomeCallBackUtils.dohomeCallBackMethod();
                                        //刷新界面
                                        HideCallbackUtils.removeCallBackMethod();
                                        closeMenu();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
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
        holder.home_item_img.setText(mDatas.get(position).getParentname());
        //所属组织
        holder.home_item_name.setText(mDatas.get(position).getOrgname());
        //最后一条消息
        holder.home_item_content.setText(mDatas.get(position).getContent());
        //最后一条消息时间
        String str = mDatas.get(position).getCreaeTime();
        str = str.substring(0, 11);
        holder.home_item_time.setText(str);
        Integer number = Integer.decode(mDatas.get(position).getUnfinish());
        int mix = 99;
        if (number > mix) {
            holder.home_item_message.setText("99+");
        } else {
            holder.home_item_message.setText(number + "");
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

    public void getData(List<Home_item> mDatas) {
        this.mDatas = mDatas;
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