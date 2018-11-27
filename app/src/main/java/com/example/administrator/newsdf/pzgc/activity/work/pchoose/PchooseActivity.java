package com.example.administrator.newsdf.pzgc.activity.work.pchoose;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 图纸查看界面（图册\标准）
 *
 * @author lx
 *         date: 2018/3/8 0008 下午 4:43
 *         update: 2018/3/8 0008
 *         version:
 */
public class PchooseActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView frPchooseAm, frPchooseMm;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pchoose);
        mContext = PchooseActivity.this;
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        //图册
        fragments.add(new PchooseFragment());
        //标准
        fragments.add(new StandardFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        //设定适配器
        vp = (ViewPager) findViewById(R.id.possse_viewpager);
        vp.setAdapter(adapter);
        frPchooseAm = (TextView) findViewById(R.id.fr_Pchoose_am);
        frPchooseMm = (TextView) findViewById(R.id.fr_Pchoose_mm);
        findViewById(R.id.com_back).setOnClickListener(this);
        frPchooseMm.setOnClickListener(this);
        frPchooseAm.setOnClickListener(this);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 1;
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
                        stands();
                        break;
                    case 1:
                        photo();
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
            case R.id.com_back:
                finish();
                break;
            case R.id.fr_Pchoose_am:
                stands();
                vp.setCurrentItem(0);
                break;
            case R.id.fr_Pchoose_mm:
                photo();
                vp.setCurrentItem(1);
                break;
            default:
                break;
        }

    }

    public void photo() {
        frPchooseMm.setTextColor(Color.parseColor("#306bb8"));
        frPchooseMm.setBackgroundResource(R.color.writer);
        frPchooseAm.setTextColor(Color.parseColor("#ffffff"));
        frPchooseAm.setBackgroundResource(R.color.colorAccent);
    }

    public void stands() {
        frPchooseAm.setTextColor(Color.parseColor("#306bb8"));
        frPchooseAm.setBackgroundResource(R.color.writer);
        frPchooseMm.setTextColor(Color.parseColor("#ffffff"));
        frPchooseMm.setBackgroundResource(R.color.colorAccent);
    }
}
