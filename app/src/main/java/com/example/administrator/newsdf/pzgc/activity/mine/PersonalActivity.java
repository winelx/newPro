package com.example.administrator.newsdf.pzgc.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人信息
 */
public class PersonalActivity extends BaseActivity {

    private Context mContext;
    private TextView username, checkname, userPhone;
    private CircleImageView imgview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        addActivity(this);
        mContext = PersonalActivity.this;
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        username = (TextView) findViewById(R.id.username);
        checkname = (TextView) findViewById(R.id.checkname);
        userPhone = (TextView) findViewById(R.id.userPhone);
        imgview = (CircleImageView) findViewById(R.id.imgage_user);
        //真实名字
        username.setText(SPUtils.getString(mContext, "staffName", ""));
        //组织名称
        checkname.setText(SPUtils.getString(mContext, "username", ""));
        //手机号
        userPhone.setText(SPUtils.getString(mContext, "phone", ""));
        String url = Requests.networks + SPUtils.getString(mContext, "portrait", null);
        LogUtil.i("url", url);
        Glide.with(this)
                .load(url)
                .thumbnail(Glide.with(this)
                        .load(R.mipmap.mine_avatar))
                .into(imgview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
