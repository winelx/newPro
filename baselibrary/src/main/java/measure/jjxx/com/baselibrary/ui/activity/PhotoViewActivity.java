package measure.jjxx.com.baselibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.R;
import measure.jjxx.com.baselibrary.adapter.PhotoViewpagerAdapter;
import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.bean.PhotoviewBean;

public class PhotoViewActivity extends BaseActivity {
    private Context mContext;
    private ArrayList<String> list;
    private TextView base_photo_title;
    private int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        //状态栏颜色修改
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#000000"));
        mContext = this;
        list = new ArrayList<>();
        Intent intent = getIntent();
        pos=intent.getIntExtra("position",0);
        list=intent.getStringArrayListExtra("photos");
        ArrayList<PhotoviewBean>mData=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mData.add(new PhotoviewBean("",list.get(i),true));
        }
        base_photo_title = (TextView) findViewById(R.id.base_photo_title);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager_activity);
        PhotoViewpagerAdapter mViewPager = new PhotoViewpagerAdapter(mData, mContext);
        viewpager.setAdapter(mViewPager);
        viewpager.setCurrentItem(pos);
        base_photo_title.setText((viewpager.getCurrentItem() + 1) + "/" + list.size());
        findViewById(R.id.base_photo_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                base_photo_title.setText((position + 1) + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
