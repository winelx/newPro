package com.example.administrator.newsdf.activity.work.pchoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.PhotoadmAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.DividerItemDecoration;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.utils.Requests;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class StandardActivity extends AppCompatActivity {
    private RecyclerView photo_rec;
    private PhotoadmAdapter photoAdapter;
    private ArrayList<PhotoBean> imagePaths;
    private TextView number, com_title, wbsname;
    private IconTextView comback;
    PostRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoadm);
        Dates.getDialog(StandardActivity.this, "请求数据中...");
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        final String wbsid = intent.getExtras().getString("wbsId");
        final String groupId = intent.getExtras().getString("groupId");
        final String Title = intent.getExtras().getString("title");
        final String status = intent.getExtras().getString("status");
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

        //GridLayout 3列
        photo_rec.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotoadmAdapter(this);
        photo_rec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        photo_rec.setAdapter(photoAdapter);
        if (status.equals("standard")) {
            request = OkGo.post(Requests.StandardList)
                    .params("WbsId", wbsid)
                    .params("page", 1)
                    .params("rows", 50);
        } else {
            request = OkGo.post(Requests.STANDARD_BY_GROUP)
                    .params("groupId", groupId);
        }

        request.execute(new StringCallback() {
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
                        filePath = Requests.networks + filePath;
                        imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                    }
                    photoAdapter.getData(imagePaths, Title, false);
                    wbsname.setText(Title + ":" + "共有" + imagePaths.size() + "张图纸");
                    Dates.disDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}