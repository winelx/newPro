package com.example.administrator.newsdf.pzgc.activity.work;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.PhotoadmAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.Requests;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description:分部分项图纸
 *
 * @author lx
 *         date: 2017/12/13 0013.
 *         version:
 */
public class PhotoadmActivity extends BaseActivity {
    private RecyclerView photo_rec;
    private PhotoadmAdapter photoAdapter;
    private ArrayList<PhotoBean> imagePaths;
    private TextView number, com_title, wbsname;
    private IconTextView comback;
    private int pages = 1;
    private String wbsid, Title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoadm);

        Dates.getDialog(PhotoadmActivity.this, "请求数据中...");
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        wbsid = intent.getExtras().getString("wbsId");
        Title = intent.getExtras().getString("title");
        SmartRefreshLayout refreshLayout = (SmartRefreshLayout) findViewById(R.id.photo_refreshLayout);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        refreshLayout.setEnableRefresh(false);
        //仿ios越界
        refreshLayout.setEnableOverScrollBounce(true);
        photo_rec = (RecyclerView) findViewById(R.id.photo_rec);
        wbsname = (TextView) findViewById(R.id.wbsname);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("分部分项图纸");
        comback = (IconTextView) findViewById(R.id.com_back);
        comback.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pages++;
                okgo();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });
        //GridLayout 3列
        photo_rec.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotoadmAdapter(this);
        photo_rec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        photo_rec.setAdapter(photoAdapter);
        okgo();
    }

    public void okgo() {
        OkGo.post(Requests.Photolist)
                .isMultipart(true)
                .params("WbsId", wbsid)
                .params("page", pages)
                .params("rows", 50)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("photo", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String id = (String) json.get("id");
                                String filePath = (String) json.get("filePath");
                                String drawingNumber = (String) json.get("drawingNumber");
                                String drawingName = (String) json.get("drawingName");
                                String drawingGroupName = (String) json.get("drawingGroupName");
                                filePath = Requests.networks + filePath;
                                imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                            }
                            photoAdapter.getData(imagePaths, Title, true);
                            wbsname.setText(Title + ":" + "共" + imagePaths.size() + "张图纸");
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
