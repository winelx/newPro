package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.administrator.newsdf.pzgc.callback.JPushCallBack;
import com.example.administrator.newsdf.pzgc.callback.JPushCallUtils;
import com.example.administrator.newsdf.pzgc.fragment.homepage.AllMessageFragment;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CollectionFragment;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CommentsFragment;
import com.example.administrator.newsdf.pzgc.fragment.homepage.HomeMineFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * description: 首页的数据的fragment
 *
 * @author lx
 *         date: 2018/1/15 0015.
 *         update: 2018/3/27 0027
 *         version:
 */
public class IndexFrament extends Fragment implements JPushCallBack, View.OnClickListener {
    private View rootView;
    private ViewPager homeageViewpager;
    private TextView homepageAll, homepageMine, homepageCollection, homepageComments;
    private Context mContext;
    private TextView homepage_mine_red;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    homepage_mine_red.setVisibility(View.VISIBLE);
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
            homepage_mine_red = rootView.findViewById(R.id.homepage_mine_red);
            mContext = getActivity();
            findById();
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new AllMessageFragment());
        fragments.add(new HomeMineFragment());
        fragments.add(new CommentsFragment());
        fragments.add(new CollectionFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getChildFragmentManager(), fragments);
        //设定适配器
        homeageViewpager.setAdapter(adapter);
        homeageViewpager.setOffscreenPageLimit(4);
        //Mianactivity接收极光推送的接口回调
        JPushCallUtils.setCallBack(this);
        homeageViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > currentPosition) {
                    //右滑
                    currentPosition = position;
                } else if (position < currentPosition) {
                    //左滑
                    currentPosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        homeAll();
                        break;
                    case 1:
                        homeMine();
                        break;
                    case 2:
                        homeComments();
                        break;
                    case 3:
                        homeCollection();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        return rootView;
    }

    //初始化控件
    private void findById() {
        homeageViewpager = rootView.findViewById(R.id.homeage_viewpager);
        homepageAll = rootView.findViewById(R.id.homepage_all);
        homepageMine = rootView.findViewById(R.id.homepage_mine);
        homepageCollection = rootView.findViewById(R.id.homepage_collection);
        homepageComments = rootView.findViewById(R.id.homepage_comments);
        homepageAll.setOnClickListener(this);
        homepageMine.setOnClickListener(this);
        homepageCollection.setOnClickListener(this);
        homepageComments.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Shop> list = LoveDao.JPushCart();
        if (list.size() > 0) {
            homepage_mine_red.setVisibility(View.VISIBLE);
        } else {
            homepage_mine_red.setVisibility(View.GONE);
        }
    }


    //推送消息后更新界面
    @Override
    public void doSomeThing() {
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    private void homeAll() {
        homepageAll.setTextColor(Color.parseColor("#F27F19"));
        homepageMine.setTextColor(Color.parseColor("#646464"));
        homepageCollection.setTextColor(Color.parseColor("#646464"));
        homepageComments.setTextColor(Color.parseColor("#646464"));
    }

    private void homeMine() {
        homepageAll.setTextColor(Color.parseColor("#646464"));
        homepageMine.setTextColor(Color.parseColor("#F27F19"));
        homepageCollection.setTextColor(Color.parseColor("#646464"));
        homepageComments.setTextColor(Color.parseColor("#646464"));
    }

    private void homeCollection() {
        homepageAll.setTextColor(Color.parseColor("#646464"));
        homepageMine.setTextColor(Color.parseColor("#646464"));
        homepageCollection.setTextColor(Color.parseColor("#646464"));
        homepageComments.setTextColor(Color.parseColor("#F27F19"));

    }

    private void homeComments() {

        homepageAll.setTextColor(Color.parseColor("#646464"));
        homepageMine.setTextColor(Color.parseColor("#646464"));
        homepageCollection.setTextColor(Color.parseColor("#F27F19"));
        homepageComments.setTextColor(Color.parseColor("#646464"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_all:
                homeageViewpager.setCurrentItem(0, false);
                break;
            case R.id.homepage_mine:
                homeageViewpager.setCurrentItem(1, false);
                break;
            case R.id.homepage_collection:
                homeageViewpager.setCurrentItem(2, false);
                break;
            case R.id.homepage_comments:
                homeageViewpager.setCurrentItem(3, false);
                break;
            default:
                break;
        }
    }
}
