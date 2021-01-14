package com.example.administrator.fengji.pzgc.activity.work;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.MainActivity;
import com.example.administrator.fengji.pzgc.activity.bright.BridhtFragment;
import com.example.administrator.fengji.pzgc.activity.bright.BridhtFragment2;
import com.example.administrator.fengji.pzgc.activity.bright.BridhtFragment3;
import com.example.baselibrary.adapter.PshooseFragAdapte;

import java.util.ArrayList;

import static com.example.administrator.fengji.R.id.bridht_company;
import static com.example.administrator.fengji.R.id.bridht_group;
import static com.example.administrator.fengji.R.id.bridht_project;


/**
 * description: 亮点展示
 *
 * @author lx
 *         date: 2018/4/19 0019 下午 4:21
 *         update: 2018/4/19 0019
 *         version:
 */

public class BrightspotFragment extends Fragment implements View.OnClickListener {
    private ViewPager bridhtViewpager;
    private TextView bridhtGroup, bridhtCompany, bridhtProject;

    private Context mContext;
    View view;
    ArrayList<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_bright, container, false);
            mContext = MainActivity.getInstance();
            //集团
            bridhtGroup = (TextView) view.findViewById(bridht_group);
            //公司
            bridhtCompany = (TextView) view.findViewById(bridht_company);
            //项目
            bridhtProject = (TextView) view.findViewById(bridht_project);
            bridhtViewpager = (ViewPager) view.findViewById(R.id.bridht_viewpager);
            bridhtGroup.setOnClickListener(this);
            bridhtCompany.setOnClickListener(this);
            bridhtProject.setOnClickListener(this);
            //处理数据
            initData();
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }


        return view;
    }

    private void initData() {
        //定义一个视图集合（用来装左右滑动的页面视图）
        fragments = new ArrayList<>();
        fragments.add(new BridhtFragment());
        fragments.add(new BridhtFragment2());
        fragments.add(new BridhtFragment3());
        PshooseFragAdapte mAdapter = new PshooseFragAdapte(getChildFragmentManager(), fragments);
        //缓存3个界面
        bridhtViewpager.setOffscreenPageLimit(3);
        bridhtViewpager.setAdapter(mAdapter);
        bridhtViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        groupselect();
                        break;
                    case 1:
                        companyselect();
                        break;
                    case 2:
                        projecrtselect();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case bridht_group:
                //集团
                bridhtViewpager.setCurrentItem(0);
                groupselect();
                break;
            case bridht_company:
                bridhtViewpager.setCurrentItem(1);
                companyselect();
                //公司
                break;
            case bridht_project:
                bridhtViewpager.setCurrentItem(2);
                projecrtselect();
                //项目
                break;
            default:
                break;
        }
    }

    //集团
    public void groupselect() {
        bridhtGroup.setTextColor(Color.parseColor("#F27F19"));
        bridhtCompany.setTextColor(Color.parseColor("#646464"));
        bridhtProject.setTextColor(Color.parseColor("#646464"));
    }

    //分公司
    public void companyselect() {
        bridhtGroup.setTextColor(Color.parseColor("#646464"));
        bridhtCompany.setTextColor(Color.parseColor("#F27F19"));
        bridhtProject.setTextColor(Color.parseColor("#646464"));
    }

    //项目
    public void projecrtselect() {
        bridhtGroup.setTextColor(Color.parseColor("#646464"));
        bridhtCompany.setTextColor(Color.parseColor("#646464"));
        bridhtProject.setTextColor(Color.parseColor("#F27F19"));
    }

}
