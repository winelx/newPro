package com.example.administrator.newsdf.pzgc.activity.home.same;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Makeup;
import com.example.baselibrary.view.BaseActivity;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:选择检查项
 *
 * @author lx
 *         date: 2018/2/6 0006 上午 11:07
 *         update: 2018/2/6 0006
 *         version:
 */
public class Checkpoint extends BaseActivity {
    private ListView listItem;
    private String list;
    private ArrayList<Makeup> mData;
    private SettingAdapter mAdapter;
    private Context mContent;
    private SPUtils spUtils;
    private IconTextView comBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoint);
        mContent = Checkpoint.this;
        mData = new ArrayList<>();
        spUtils = new SPUtils();
        listItem = (ListView) findViewById(R.id.list_item);
        try {
            Intent intent = getIntent();
            list = intent.getExtras().getString("wbsID");
            if (list.length() > 0) {
                okgo(list);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        comBack = (IconTextView) findViewById(R.id.com_back);
        comBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new SettingAdapter<Makeup>(mData, R.layout.choose_item) {
            @Override
            public void bindView(ViewHolder holder, final Makeup obj) {
                holder.setText(R.id.check, obj.getName());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //返回列表
                        Intent intent = new Intent();
                        //内容
                        intent.putExtra("name", obj.getName());
                        //内容
                        intent.putExtra("id", obj.getId());
                        //回传数据到主Activity
                        setResult(2, intent);
                        //此方法后才能返回主Activity
                        finish();
                    }
                });
            }

        };
        listItem.setAdapter(mAdapter);

    }

    void okgo(String str) {
        OkGo.post(Requests.WbsTaskGroup)
                .params("wbsId", str)
                .params("isNeedTotal", "true")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("ss", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String id = json.getString("id");
                                String name = json.getString("detectionName");
                                mData.add(new Makeup(name, id));
                            }
                            mAdapter.getData(mData);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
