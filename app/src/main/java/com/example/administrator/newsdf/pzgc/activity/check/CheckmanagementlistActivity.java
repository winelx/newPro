package com.example.administrator.newsdf.pzgc.activity.check;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckManagementAdapter;

import java.util.ArrayList;

/**
 * description: 检查管理_标准的检查任务
 *
 * @author lx
 *         date: 2018/8/2 0002 下午 2:41
 *         update: 2018/8/2 0002
 *         version:
 */
public class CheckmanagementlistActivity extends AppCompatActivity {
    private RecyclerView rmanageRecy;
    private CheckManagementAdapter mAdapter;
    private ArrayList<String> list;
    private ImageView com_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagementlist);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("aaaa");
        }
        com_img = (ImageView) findViewById(R.id.com_img);
        com_img.setVisibility(View.INVISIBLE);
        rmanageRecy = (RecyclerView) findViewById(R.id.rmanage_recy);
        //设置布局管理器
        rmanageRecy.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        rmanageRecy.setAdapter(mAdapter = new CheckManagementAdapter(this));
        //设置控制Item增删的动画
        rmanageRecy.setItemAnimator(new DefaultItemAnimator());
        mAdapter.getData(list);
    }
}
