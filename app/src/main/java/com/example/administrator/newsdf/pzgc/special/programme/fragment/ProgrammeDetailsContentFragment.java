package com.example.administrator.newsdf.pzgc.special.programme.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeApprovalActivity;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeAuditorActivity;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 15:23
 * @说明 施工方案详情:内容
 **/

public class ProgrammeDetailsContentFragment extends LazyloadFragment implements View.OnClickListener {
    private Button approval;

    @Override
    protected int setContentView() {
        return R.layout.fragment_programmedetails_content;
    }

    @Override
    protected void init() {
        approval = (Button) findViewById(R.id.approval);
        approval.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.approval:
                startActivity(new Intent(getContext(), ProgrammeApprovalActivity.class));
                break;
            default:
                break;
        }
    }
}
