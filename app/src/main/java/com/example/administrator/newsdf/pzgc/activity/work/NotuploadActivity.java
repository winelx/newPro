package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.home.same.ReplyActivity;
import com.example.administrator.newsdf.pzgc.Adapter.Adapter;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

/**
 * description: 未上传资料列表界面
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:21
 * update: 2018/2/6 0006
 * version:
 */

public class NotuploadActivity extends BaseActivity implements TaskCallback {
    private LinearLayout toolbar_add;
    private LinearLayout com_back, nullposion;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private List<Shop> list;
    int pos = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notupload);
        toolbar_add = (LinearLayout) findViewById(R.id.toolbar_add);
        toolbar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotuploadActivity.this, ReplyActivity.class);
                intent.putExtra("position", -1);
                startActivityForResult(intent, 1);
            }
        });
        list = new ArrayList<>();
        TaskCallbackUtils.setCallBack(this);
        com_back = (LinearLayout) findViewById(R.id.com_back);
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        nullposion = (LinearLayout) findViewById(R.id.nullposion);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        mRecyclerView.setAdapter(mAdapter = new Adapter(this));
        //设置控制Item增删的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onStart() {
        super.onStart();
        queryDate();
    }


    //更新数据
    private void queryDate() {
        list.clear();
        //从数据库获取数据
        list = LoveDao.queryLove();
        mAdapter.getData(list);
        if (list.size() > 0) {
            nullposion.setVisibility(View.GONE);
        } else {
            nullposion.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (!list.isEmpty()) {
                queryDate();
            }
        } else if (requestCode == 2 && resultCode == 2) {
//            deleteDate(pos);
            queryDate();
        }

    }

    //删除记录
    public void deleteDate(int pos) {
        if (!list.isEmpty()) {
            //从数据库中删除数据
            LoveDao.deleteLove(list.get(pos).getId());
        }
        mAdapter.closeMenu();
        queryDate();
    }


    //点击条目跳转界面
    public void getInt(int position) {
        pos = position;
        Intent intent = new Intent(this, ReplyActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("content", list.get(position).getContent());
        intent.putExtra("wbsname", list.get(position).getName());
        intent.putExtra("id", list.get(position).getWebsid());
        intent.putExtra("Imgpath", list.get(position).getImage_url());
        intent.putExtra("Checkid", list.get(position).getCheckid());
        intent.putExtra("Checkname", list.get(position).getCheckname());
        intent.putExtra("notup", "notup");
        intent.putExtra("title", "任务编辑");
        startActivityForResult(intent, 2);
    }

    @Override
    public void taskCallback() {
        queryDate();
    }
}
