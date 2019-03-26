package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.PhotoadmAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.baselibrary.view.BaseActivity;
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

/**
 * description: 图纸详情
 *
 * @author lx
 *         date: 2018/3/26 0026 上午 9:44
 *         update: 2018/3/26 0026
 *         version:
 */
public class ListPhActivity extends BaseActivity {
    private RecyclerView photo_rec;
    private PhotoadmAdapter photoAdapter;
    private ArrayList<PhotoBean> imagePaths;
    private TextView number, com_title, wbsname;
    private IconTextView comback;
    PostRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ph);
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        final String groupId = intent.getExtras().getString("groupId");
        final String Title = intent.getExtras().getString("title");
        SmartRefreshLayout refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableLoadmore(false);//禁止上拉
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableOverScrollBounce(true);//仿ios越界
        photo_rec = (RecyclerView) findViewById(R.id.photo_rec);
        wbsname = (TextView) findViewById(R.id.wbsname);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("图纸详情");
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
        photo_rec.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotoadmAdapter(this);
        photo_rec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        photo_rec.setAdapter(photoAdapter);

        OkGo.post(Requests.Photo_ce)
                .params("groupId", groupId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
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
                                imagePaths.add(new PhotoBean(id, filePath, drawingNumber,
                                        drawingName, drawingGroupName));
                            }
                            photoAdapter.getData(imagePaths, Title, true);
                            wbsname.setText(Title + ":" + "共有" + imagePaths.size() + "张图纸");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }
}
