package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProDetails;
import com.example.administrator.newsdf.pzgc.special.programme.fragment.ProgrammeDetailScircuitFragment;
import com.example.administrator.newsdf.pzgc.special.programme.fragment.ProgrammeDetailsContentFragment;
import com.example.administrator.newsdf.pzgc.special.programme.model.ProgrammeDetailsModel;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.base.BaseViewModel;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 15:39
 * @说明 详情页面
 **/

public class ProgrammeDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView checkDownAll, checkDownMe, titleView;
    private ViewPager checkDownViewpager;
    private ArrayList<Fragment> mFragment;
    private PshooseFragAdapte adapter;
    private ProgrammeDetailsModel model;
    private Observer<ProDetails> Observer;
    private Intent intent;
    private Context mContext;
    private String orgid, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkdown_message);
        intent = getIntent();
        mContext = this;
        mFragment = new ArrayList<>();
        mFragment.add(new ProgrammeDetailsContentFragment());
        mFragment.add(new ProgrammeDetailScircuitFragment());
        adapter = new PshooseFragAdapte(getSupportFragmentManager(), mFragment);
        checkDownMe = (TextView) findViewById(R.id.check_down_me);
        checkDownAll = (TextView) findViewById(R.id.check_down_all);
        checkDownAll.setText("详情");
        checkDownMe.setText("流程");
        titleView = (TextView) findViewById(R.id.com_title);
        titleView.setText("专项施工方案管理");
        checkDownViewpager = (ViewPager) findViewById(R.id.check_down_viewpager);
        checkDownMe.setOnClickListener(this);
        checkDownAll.setOnClickListener(this);
        checkDownViewpager.setAdapter(adapter);
        findViewById(R.id.com_back).setOnClickListener(this);
        checkDownViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 1;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > currentPosition) {
                    //右滑
                    currentPosition = position;
                } else if (position < currentPosition) {
                    //左滑
                    currentPosition = position;
                }
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        all();
                        break;
                    case 1:
                        mine();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        LiveDataBus.get().with("prodetails", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        reqeuset();
                    }
                });
        model = ViewModelProviders.of(this).get(ProgrammeDetailsModel.class);
        Observer = new Observer<ProDetails>() {
            @Override
            public void onChanged(@Nullable ProDetails prodetails) {
                LiveDataBus.get().with("prodetails_content").setValue(prodetails);
                LiveDataBus.get().with("prodetails_scr").setValue(prodetails);
            }
        };
        model.setRequestError(new BaseViewModel.Modelinface() {
            @Override
            public void onerror() {
                ToastUtils.showShortToast("请求失败");
            }
        });
        reqeuset();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_down_me:
                mine();
                break;
            case R.id.check_down_all:
                all();
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void all() {
        checkDownMe.setTextColor(Color.parseColor("#050505"));
        checkDownAll.setTextColor(Color.parseColor("#5096F8"));
        checkDownViewpager.setCurrentItem(0);
    }

    private void mine() {
        checkDownMe.setTextColor(Color.parseColor("#5096F8"));
        checkDownAll.setTextColor(Color.parseColor("#050505"));
        checkDownViewpager.setCurrentItem(1);
    }

    public void reqeuset() {
        String taskid = "";
        taskid = intent.getStringExtra("taskid");
        id = intent.getStringExtra("id");
        orgid = intent.getStringExtra("orgid");
        model.getData(id, taskid).observe(this, Observer);
    }

    public void getJurisdiction(JurisdictionCallback callback) {
        //获取相机权限，定位权限，内存权限
        requestRunPermisssion(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                //表示所有权限都授权了
                callback.success();
            }
            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(mContext, "被拒绝的权限：" +
                            permission, Toast.LENGTH_SHORT).show();
                }
                callback.error();
            }
        });
    }

    public String getOrgid() {
        return orgid;
    }

    public String getId() {
        return id;
    }

    public interface JurisdictionCallback {
        void success();

        void error();
    }

}
