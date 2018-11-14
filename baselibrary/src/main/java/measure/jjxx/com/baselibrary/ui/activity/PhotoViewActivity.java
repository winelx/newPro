package measure.jjxx.com.baselibrary.ui.activity;

import android.annotation.SuppressLint;
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

/**
 * description: 图片预览界面
 *
 * @author lx
 *         date: 2018/10/26 0026 下午 2:39
 *         update: 2018/10/26 0026
 *         version:
 */
public class PhotoViewActivity extends BaseActivity {
    private Context mContext;
    private ArrayList<String> listpath;
    private ArrayList<String> liststatus;
    private ArrayList<String> listname;
    private TextView base_photo_title;
    private boolean photos_name_lean=false;
    private int pos = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        //状态栏颜色修改
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#000000"));
        mContext = this;
        listpath = new ArrayList<>();
        liststatus = new ArrayList<>();
        listname = new ArrayList<>();
        Intent intent = getIntent();
        //图片当前位置
        pos = intent.getIntExtra("item_position", 0);
        //图片地址
        listpath = intent.getStringArrayListExtra("photos_path");
        //图片状态
        liststatus = intent.getStringArrayListExtra("photos_status");
        //图片名字
        listname = intent.getStringArrayListExtra("photos_name");
        photos_name_lean = intent.getBooleanExtra("photos_name_lean",false);
        //传递到适配器的数据集合
        ArrayList<PhotoviewBean> mData = new ArrayList<>();
        //组装数据，将传递过来的数据组装
        for (int i = 0; i < listpath.size(); i++) {
            //图片名字，图片地址， 图片状态
            mData.add(new PhotoviewBean(listname.get(i), listpath.get(i), liststatus.get(i)));
        }
        //初始化控件，用来显示当前图片的位置和图片总数
        base_photo_title = (TextView) findViewById(R.id.base_photo_title);
        //初始化数据
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager_activity);
        //viewpager的是适配器
        PhotoViewpagerAdapter mViewPager = new PhotoViewpagerAdapter(mData, photos_name_lean,mContext);
        viewpager.setAdapter(mViewPager);
        //跳转到指定的图片位置
        viewpager.setCurrentItem(pos);
        //显示
        base_photo_title.setText((viewpager.getCurrentItem() + 1) + "/" + listpath.size());
        //退出
        findViewById(R.id.base_photo_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();
            }
        });
        //viewpager 的滑动监听，用来出标题处的图片显示 的位置
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                base_photo_title.setText((position + 1) + "/" + listpath.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
