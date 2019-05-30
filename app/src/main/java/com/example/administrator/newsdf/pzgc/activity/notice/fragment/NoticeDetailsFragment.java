package com.example.administrator.newsdf.pzgc.activity.notice.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;

import androidx.navigation.Navigation;

/**
 * @author： lx
 * @创建时间： 2019/5/29 0029 10:24
 * @说明： 通知公告详情
 **/
public class NoticeDetailsFragment extends LazyloadFragment implements View.OnClickListener {
    private LinearLayout titlel;
    private TextView com_title;

    @Override
    protected int setContentView() {
        return R.layout.activity_check_task_web;
    }

    @Override
    protected void init() {
        com_title = rootView.findViewById(R.id.com_title);
        com_title.setText("公告详情");
        titlel = rootView.findViewById(R.id.toolbar_title);
        titlel.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.com_back).setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                Navigation.findNavController(v).navigateUp();
                break;
            default:
                break;
        }
    }
}
