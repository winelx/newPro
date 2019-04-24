package com.example.administrator.newsdf.pzgc.activity.work;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.UploadPhAdapter;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.newsdf.R.id.Up_ph_back;

/**
 * description: 离线图片
 *
 * @author lx
 *         date: 2018/3/14 0014 下午 1:24
 *         update: 2018/3/14 0014
 *         version:
 */
public class UploadPhotoActivity extends BaseActivity {
    private List<Shop> listPath;
    private Context mContext;
    private UploadPhAdapter mAdapter;
    private TextView upload_name, upload_photo, upload_number, Up_ph_title;
    private String status;
    ArrayList<Shop> path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        mContext = UploadPhotoActivity.this;
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //名称
        upload_name = (TextView) findViewById(R.id.upload_name);
        //图片
        upload_photo = (TextView) findViewById(R.id.upload_photo);
        //编号
        upload_number = (TextView) findViewById(R.id.upload_number);
        Up_ph_title = (TextView) findViewById(R.id.Up_ph_title);
        Intent intent = getIntent();
        status = intent.getExtras().getString("status");
        if ("standard".equals(status)) {
            upload_name.setText("标准名称");
            upload_photo.setText("所属标准分类");
            upload_number.setText("标准编号");
            Up_ph_title.setText("离线标准");
        }

        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        mRecyclerView.setAdapter(mAdapter = new UploadPhAdapter(this));
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置控制Item增删的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        findViewById(Up_ph_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        queryDate();
    }

    /**
     * 拿到数据
     */
    private void queryDate() {
        if ("standard".equals(status)) {
            listPath = new ArrayList<>();
            //或者指定数据
            listPath = LoveDao.queryCart();
            path = new ArrayList<>();
            for (Shop shop : listPath) {
                String str = shop.getProject();
                if ("standard".equals(str)) {
                    path.add(shop);
                }
            }
            mAdapter.getData(path);
        } else {
            listPath = new ArrayList<>();
            //或者指定数据
            listPath = LoveDao.queryCart();
            path = new ArrayList<>();
            for (Shop shop : listPath) {
                shop.getProject();
                if (shop.getProject() == null) {
                    path.add(shop);
                }
            }
            mAdapter.getData(path);
        }

    }


    /**
     * 左滑删除记录
     */
    public void deleteDate(int position) {
        if (!path.isEmpty()) {
            LoveDao.deleteLove(path.get(position).getId());
        }
        mAdapter.closeMenu();
        queryDate();
    }

    /**
     * 点击条目跳转界面
     */
    public void getInt(int position) {
        ArrayList<String> path = new ArrayList<>();
        ArrayList<String> imagepath = new ArrayList<>();
        for (int i = 0; i < listPath.size(); i++) {
            path.add(listPath.get(i).getImage_url());
            imagepath.add(listPath.get(i).getContent());
        }
        PhotoPreview.builder().setPhotos(path).setCurrentItem(position).
                setShowDeleteButton(false).setShowUpLoadeButton(false).setImagePath(imagepath)
                .start((Activity) mContext);
    }



}
