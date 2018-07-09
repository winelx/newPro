package com.example.administrator.newsdf.pzgc.activity.home.same;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Icon;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class WorkareaActivity extends AppCompatActivity {
    private ListView uslistView;
    private SettingAdapter<Icon> mAdapter = null;
    private ArrayList<Icon> mData;
    private Context mContext;
    private LinearLayout mine_backgroud,com_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordarea);
        mData = new ArrayList<>();
        mContext = this;
        getData();
        mine_backgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        uslistView = (ListView) findViewById(R.id.us_listView);
        mAdapter = new SettingAdapter<Icon>(mData, R.layout.contact_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, final Icon obj) {
                //名字
                holder.setText(R.id.content_name, obj.getName());
                holder.setVisibility(R.id.contact_acatar, View.GONE);
                holder.setVisibility(R.id.content_phone, View.GONE);
                holder.setOnClickListener(R.id.member, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newpush = new Intent();
                        newpush.putExtra("name", obj.getName());
                        newpush.putExtra("userId", obj.getId());
                        //回传数据到主Activity
                        setResult(3, newpush);
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        findViewById(R.id.workarea_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        uslistView.setAdapter(mAdapter);
    }

    public void getData() {
        OkGo.post(Requests.WORK_AREA)
                .params("dictName", "workArea")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray json = jsonObject.getJSONArray("data");
                            if (json.length() > 0) {
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject js = json.getJSONObject(i);
                                    mData.add(new Icon(js.getString("value"), "", js.getString("label"), "", ""));
                                }
                                mAdapter.getData(mData);
                                mine_backgroud.setVisibility(View.GONE);
                            } else {
                                mine_backgroud.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
