package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：整改回复单验证
 * {@link }
 */
@SuppressLint("Registered")
public class ChagedReplyVerificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView categoryItem;
    private EditText replyDescription;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reply_verification);
        mContext = this;
        findViewById(R.id.validation_status).findViewById(R.id.validation_status);
        categoryItem = (TextView) findViewById(R.id.category_item);
        replyDescription = (EditText) findViewById(R.id.replyDescription);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.validation_status:
                AlertDialog alertDialog2 = new AlertDialog.Builder(mContext)
                        .setTitle("提交")
                        .setMessage("是否验证通过")
                        .setPositiveButton("通过", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*删除按钮*/
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("打回", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog2.show();
                break;
            default:
                break;
        }
    }
}
