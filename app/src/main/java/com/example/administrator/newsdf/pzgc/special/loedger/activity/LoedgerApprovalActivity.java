package com.example.administrator.newsdf.pzgc.special.loedger.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.base.BaseActivity;

/**
 * @Author lx
 * @创建时间 2019/8/5 0005 17:07
 * @说明 台账审核
 **/

public class LoedgerApprovalActivity extends BaseActivity implements View.OnClickListener {
    private String status;
    private TextView category_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loedgerapproval);
        findViewById(R.id.com_back).setOnClickListener(this);
        category_item = findViewById(R.id.category_item);
        findViewById(R.id.approvaluser).setOnClickListener(this);
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
            default:
                break;
        }
    }
}
