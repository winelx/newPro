package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.callback.JPushCallBack;
import com.example.administrator.newsdf.callback.JPushCallUtils;

import java.util.List;

import static com.example.administrator.newsdf.R.id.index_point_red;


/**
 * description: 首页的数据的fragment
 *
 * @author lx
 *         date: 2018/1/15 0015.
 *         update: 2018/3/27 0027
 *         version:
 */
public class IndexFrament extends Fragment implements View.OnClickListener, JPushCallBack {
    private View rootView;
    private HomeFragment home;
    private AllMessageFragment message;
    TextView mMessage, aMessage, pointRed;

    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    pointRed.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_index, null);
            //全部
            mMessage = rootView.findViewById(R.id.fr_index_mm);
            //我的
            aMessage = rootView.findViewById(R.id.fr_index_am);
            pointRed = rootView.findViewById(index_point_red);
            mContext = getActivity();
            mMessage.setOnClickListener(this);
            aMessage.setOnClickListener(this);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //推送
        JPushCallUtils.setCallBack(this);
        //第一次初始化首页默认显示第一个fragment
        initFragment2();
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        List<Shop> list= LoveDao.JPushCart();
        if (list.size() > 0) {

            pointRed.setVisibility(View.VISIBLE);
        }else {
            pointRed.setVisibility(View.GONE);
        }
    }

    //显示第一个fragment
    private void initFragment1() {
        //这是文字颜色
        mMessage.setTextColor(Color.parseColor("#5096F8"));
        aMessage.setTextColor(Color.parseColor("#f5f4f4"));
        //设置背景样式
        aMessage.setBackgroundResource(R.drawable.fr_home_mm_f);
        mMessage.setBackgroundResource(R.drawable.fr_home_am);

        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //第二种方式(replace)，初始化fragment
        if (home == null) {
            home = new HomeFragment();
        }
        transaction.replace(R.id.main_frame_layout, home);
        //提交事务
        transaction.commit();
    }

    /**
     * 显示第一个fragment
     */
    private void initFragment2() {
        aMessage.setTextColor(Color.parseColor("#5096F8"));
        mMessage.setTextColor(Color.parseColor("#f5f4f4"));
        aMessage.setBackgroundResource(R.drawable.fr_home_mm);
        mMessage.setBackgroundResource(R.drawable.fr_home_am_f);

        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //第二种方式(replace)，初始化fragment
        if (message == null) {
            message = new AllMessageFragment();
        }
        transaction.replace(R.id.main_frame_layout, message);
        //提交事务
        transaction.commit();
    }
    //时间点击
    @Override
    public void onClick(View v) {
        if (v == mMessage) {
            initFragment1();
        } else if (v == aMessage) {
            initFragment2();
        }
    }

    //更新界面
    @Override
    public void doSomeThing(String string) {
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);

    }
}
