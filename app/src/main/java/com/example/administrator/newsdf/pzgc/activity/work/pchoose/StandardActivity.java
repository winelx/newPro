package com.example.administrator.newsdf.pzgc.activity.work.pchoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.PhotolabelAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.DividerItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

//标准图纸列表界面（图册的在photoadmActivity）
public class StandardActivity extends AppCompatActivity {
    private RecyclerView photo_rec;
    private PhotolabelAdapter photoAdapter;
    private ArrayList<PhotoBean> imagePaths;
    private TextView number, com_title, wbsname;
    private IconTextView comback;
    private SmartRefreshLayout refreshlayout;
    private PostRequest request;
    private String wbsid, groupId, Title, status;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        Dates.getDialog(StandardActivity.this, "请求数据中...");
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        Title = intent.getExtras().getString("title");
        groupId = intent.getExtras().getString("groupId");
        status = intent.getExtras().getString("status");
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        refreshlayout.setEnableOverScrollBounce(true);
        photo_rec = (RecyclerView) findViewById(R.id.photo_rec);
        wbsname = (TextView) findViewById(R.id.wbsname);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("标准");
        comback = (IconTextView) findViewById(R.id.com_back);
        comback.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        //是否在加载的时候禁止列表的操作
        refreshlayout.setDisableContentWhenLoading(true);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //GridLayout 3列
        photo_rec.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotolabelAdapter(this);
        photo_rec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        photo_rec.setAdapter(photoAdapter);
        OkGo();
//        //上拉加载
//        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @TargetApi(Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                page++;
//                OkGo();
//                //传入false表示加载失败
//                refreshlayout.finishLoadmore(800);
//            }
//        });

    }

    public void OkGo() {
        if (status.equals("standard")) {
            request = OkGo.post(Requests.StandardList)
                    .params("WbsId", groupId);
        } else {
            request = OkGo.post(Requests.STANDARD_BY_GROUP)
                    .params("groupId", groupId);
        }
        request
                .isMultipart(true)
                .params("page", page)
                .params("rows", 10).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        String id = (String) json.get("id");
                        String filePath = (String) json.get("filePath");
                        filePath = Requests.networks + filePath;
                        String drawingNumber;
                        try {
                            drawingNumber = (String) json.get("standardNumber");

                        } catch (JSONException e) {
                            drawingNumber = "";
                        }
                        String drawingName;
                        try {
                            drawingName = (String) json.get("standardName");
                        } catch (JSONException e) {
                            drawingName = "";
                        }
                        String drawingGroupName;
                        try {
                            drawingGroupName = (String) json.get("standardGroupName");
                        } catch (JSONException e) {
                            drawingGroupName = "";
                        }

                        imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                    }
                    photoAdapter.getData(imagePaths, Title, true);
                    wbsname.setText(Title + ":" + "共" + imagePaths.size() + "标准");
                    Dates.disDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}