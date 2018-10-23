package com.example.zcjlmodule.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.zcjlmodule.R;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.adapter.PhotosAdapter;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;


/**
 * description:查看征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 4:50
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面StandardDecomposeZcActivity
 */
public class ExamineDismantlingActivity extends AppCompatActivity {
    private PhotosAdapter photosAdapter;
    private Context mContext;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_examine_dismantling);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("查看征拆标准");
        photosAdapter = new PhotosAdapter(mContext, list, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.examine_layout_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photosAdapter);
        photosAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {

            @Override
            public void addlick(View view, int position) {
                ToastUtlis.getInstance().showShortToast("添加");
            }

            @Override
            public void photoClick(View view, int position) {
                ToastUtlis.getInstance().showShortToast("11");
            }
        });
    }
}
