package com.example.administrator.newsdf.pzgc.activity.changed;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.mine.Text;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/1/30 0030}
 * 描述：修改后的新增整改通知单
 * {@link }
 */
public class ChangedNewActivity extends BaseActivity implements View.OnClickListener {
    private TextView chaged_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_new);
        //返回
        findViewById(R.id.com_back).setOnClickListener(this);
        //下发
        findViewById(R.id.chaged_release_problem).setOnClickListener(this);
        //导入问题项
        findViewById(R.id.chaged_import_problem).setOnClickListener(this);
        //添加问题项
        findViewById(R.id.chaged_add_problem).setOnClickListener(this);
        //整改负责人
        findViewById(R.id.chaged_head_lin).setOnClickListener(this);
        //下发人
        findViewById(R.id.chaged_release_lin).setOnClickListener(this);
        //整改组织
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.chaged_release_problem:
                //下发
                ToastUtils.showShortToast("下发");
                break;
            case R.id.chaged_import_problem:
                ToastUtils.showShortToast("导入问题项");
                //
                break;
            case R.id.chaged_add_problem:
                ToastUtils.showShortToast("添加问题项");
                break;
            case R.id.chaged_head_lin:
                ToastUtils.showShortToast("整改负责人");
                break;
            case R.id.chaged_release_lin:
                ToastUtils.showShortToast("下发人");
                break;
            case R.id.chaged_organize_lin:
                ToastUtils.showShortToast("整改组织");
                break;
            default:
                break;
        }
    }
}
