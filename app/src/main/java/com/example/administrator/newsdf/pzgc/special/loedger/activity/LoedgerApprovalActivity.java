package com.example.administrator.newsdf.pzgc.special.loedger.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.rx.LiveDataBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lx
 * @创建时间 2019/8/5 0005 17:07
 * @说明 台账审核
 **/

public class LoedgerApprovalActivity extends BaseActivity implements View.OnClickListener {
    private String status;
    private TextView category_item;
    private EditText replyDescription;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loedgerapproval);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        findViewById(R.id.com_back).setOnClickListener(this);
        category_item = findViewById(R.id.category_item);
        replyDescription = findViewById(R.id.replyDescription);
        findViewById(R.id.approvaluser).setOnClickListener(this);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.approvaluser:
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("是否验证通过")
                        .setNegativeButton("打回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                status = "2";
                                category_item.setText("打回");
                                category_item.setTextColor(Color.parseColor("#FE0000"));

                            }
                        }).setPositiveButton("通过", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //处理确认按钮的点击事件
                                category_item.setText("通过");
                                category_item.setTextColor(Color.parseColor("#28c26A"));
                                status = "1";
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.com_back:
                finish();
                break;
            case R.id.btn:
                request();
                break;
            default:
                break;
        }
    }

    public void request() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("isby", status);
        map.put("verificationOpinion", replyDescription.getText().toString());
        NetWork.postHttp(Api.VERIFICATIONPAGE, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    ToastUtils.showShortToast(jsonObject.getString("msg"));
                    if (ret == 0) {
                        LiveDataBus.get().with("details").setValue("");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }
}
