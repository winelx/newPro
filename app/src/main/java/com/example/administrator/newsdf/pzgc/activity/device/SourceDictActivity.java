package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.Icon;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.view.BaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @data :2019/3/27 0027
 * @描述 :获取设备来源字典接口
 * @see
 */
public class SourceDictActivity extends BaseActivity {
    private ArrayList<Audio> list;
    private SettingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);
        list = new ArrayList<>();
        TextView title = findViewById(R.id.com_title);
        title.setText("设备来源");
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ListView listView = findViewById(R.id.wbs_listview);
        adapter = new SettingAdapter<Audio>(list, R.layout.task_category_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, final Audio obj) {
                //名字
                holder.setText(R.id.category_content, obj.getName());
                holder.setOnClickListener(R.id.category_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newpush = new Intent();
                        newpush.putExtra("name", obj.getName());
                        newpush.putExtra("value", obj.getContent());
                        //回传数据到主Activity
                        setResult(2, newpush);
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        request();
    }

    public void request() {
        OkGo.get(Requests.getSourceDict)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String label = json.getString("label");
                                    String value = json.getString("value");
                                    list.add(new Audio(label, value));
                                }
                                adapter.getData(list);
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast("请求失败");
                    }
                });
    }
}
